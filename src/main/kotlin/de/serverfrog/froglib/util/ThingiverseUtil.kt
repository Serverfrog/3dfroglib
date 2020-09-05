package de.serverfrog.froglib.util

import de.serverfrog.froglib.config.ConfigFactory
import tornadofx.*
import java.io.*
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import javax.net.ssl.HttpsURLConnection


object ThingiverseUtil {

    const val regex = "https:\\/\\/www\\.thingiverse\\.com\\/thing:\\d+"

    fun downloadAndSave(url: String, status: TaskStatus, onComplete: () -> Unit) {
        runAsync(status) {
            updateTitle("Download from Thingiverse")
            val downloadPair = downloadFile(url)

            extract(downloadPair)

        } ui {
            onComplete()
        }
    }

    private fun FXTask<*>.downloadFile(url: String): Pair<File, String> {
        updateMessage("Downloading ZIP for $url")
        if (!url.matches(Regex(regex))) {
            throw UnsupportedOperationException("Url isn't a thingiverse URL. $url")
        }

        val con: HttpsURLConnection = URL("$url/zip").openConnection() as HttpsURLConnection
        con.requestMethod = "GET"
        val tempfile = Files.createTempFile("thingiverse", ".zip").toFile()
        val chunksize = 1024
        val contentsize = con.getHeaderField("Content-Length").toLong()
        val name = getName(con)

        val buffer = ByteArrayOutputStream()
        val inputStream = con.inputStream
        var nRead: Int
        val data = ByteArray(chunksize)
        var chungsRead = 0L

        while (inputStream.read(data, 0, data.size).also { nRead = it } != -1) {
            buffer.write(data, 0, nRead)
            chungsRead += chunksize
            updateProgress(chungsRead, contentsize)
        }
        buffer.flush()
        tempfile.writeBytes(buffer.toByteArray())
        return Pair(tempfile, name)
    }

    fun getName(con: HttpsURLConnection): String {
        val url = con.url
        val name = URLDecoder.decode(url.path.split('/').last(), StandardCharsets.UTF_8.toString())
        return name.split('.').first().trim()

    }

    fun FXTask<*>.extract(filePair: Pair<File, String>) {
        var name = filePair.second
        updateMessage("decompress ZIP for $name")
        updateProgress(0, 1)
        val libFolder = getAndCreateLibFolder()
        name = name.filter { c -> c.isLetterOrDigit() }
        val downloadFolder = libFolder.resolve(name)
        createFolderIfNotExists(downloadFolder)
        unzip(filePair.first, downloadFolder.toFile())
    }


    fun FXTask<*>.unzip(file: File, dest: File) {
        val buffer = ByteArray(1024)
        val zis = ZipInputStream(FileInputStream(file))
        var zipEntry = zis.nextEntry
        while (zipEntry != null) {
            val newFile: File = newFile(dest, zipEntry)
            if (zipEntry.isDirectory) {
                zipEntry = zis.nextEntry
                continue
            }

            val fos = FileOutputStream(newFile)
            var len = 0
            var current = 0L

            updateMessage("decompress ${zipEntry.name}")
            updateProgress(current, len.toLong())
            while (zis.read(buffer).also { len = it } > 0) {
                fos.write(buffer, 0, len)
                current += len
                updateProgress(current, len.toLong())
            }
            fos.close()
            zipEntry = zis.nextEntry
        }
        zis.closeEntry()
        zis.close()
    }


    @Throws(IOException::class)
    fun newFile(destinationDir: File, zipEntry: ZipEntry): File {
        val destFile = File(destinationDir, zipEntry.name)
        val destDirPath = destinationDir.canonicalPath
        val destFilePath = destFile.canonicalPath
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw IOException("Entry is outside of the target dir: " + zipEntry.name)
        }
        if (zipEntry.isDirectory) {
            destFile.mkdirs()
        } else {
            Files.createFile(destFile.toPath())
        }
        return destFile
    }

    fun getAndCreateLibFolder(): Path {
        val config = ConfigFactory.getConfig()
        val libFolder = config.saveLocation.resolve("lib")
        createFolderIfNotExists(libFolder)
        return libFolder
    }

    private fun createFolderIfNotExists(folder: Path) {
        if (!folder.toFile().exists() || !folder.toFile().isDirectory) {
            folder.toFile().mkdirs()
        }
    }

}