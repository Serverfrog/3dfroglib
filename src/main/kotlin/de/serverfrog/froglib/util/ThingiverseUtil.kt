package de.serverfrog.froglib.util

import tornadofx.*
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files


object ThingiverseUtil {

    const val regex = "https:\\/\\/www\\.thingiverse\\.com\\/thing:\\d+"

    fun downloadAndSave(url: String, status: TaskStatus, onComplete: () -> Unit) {
        runAsync(status) {
            updateTitle("Download from Thingiverse")
            updateMessage("Downloading ZIP for $url")
            if (!url.matches(Regex(regex))) {
                throw UnsupportedOperationException("Url isn't a thingiverse URL. $url")
            }

            val con: HttpURLConnection = URL("$url/zip").openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            val tempfile = Files.createTempFile("thingiverse", ".zip").toFile()
            val chunksize = 1024
            val contentsize = con.headerFields["Content-Length"]!![0]!!.toLong()

            val buffer = ByteArrayOutputStream()
            val inputStream = con.inputStream
            var nRead: Int
            val data = ByteArray(chunksize)
            var chungsRead = 0L

            while (inputStream.read(data, 0, data.size).also { nRead = it } != -1) {
                buffer.write(data, 0, nRead)
                chungsRead += chunksize
                updateProgress(chungsRead,contentsize)
            }
            buffer.flush()
            tempfile.writeBytes(buffer.toByteArray())

        } ui {
            onComplete()
        }
    }


}