package de.serverfrog.froglib.config

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import io.github.config4k.extract
import io.github.config4k.toConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class ConfigFactory {


    companion object {
        enum class OS {
            WINDOWS, LINUX, MAC
        }

        private var os: OS = initOS()

        val logger: Logger = LoggerFactory.getLogger(ConfigFactory::class.java)

        fun getConfig(): Config {
            val configFile = getUserConfigPath().resolve("user.config")
            val exists = Files.exists(configFile)
            if (!exists) {
                createConfig(configFile)
            }
            val configString = String(Files.readAllBytes(configFile))
            return ConfigFactory.parseString(configString).extract("config")

        }

        fun save(config: Config){
            val configFile = getUserConfigPath().resolve("user.config")
            val defaults = ConfigRenderOptions.defaults()
            val render = config.toConfig("config")
                    .root().render(defaults)

            Files.write(configFile, render.toByteArray())
        }

        fun getUserConfigPath(): Path {
            val configPath = StringBuilder()
            if (os == OS.WINDOWS) {
                configPath.append(System.getenv("APPDATA"))
            } else {
                configPath.append(System.getProperty("user.home"))
            }
            configPath.append("/.3dfroglib/")
            val path = Paths.get(configPath.toString())
            if (!Files.exists(path)) {
                Files.createDirectories(path)
            }
            return path
        }


        fun createConfig(configFile: Path) {
            val defaults = ConfigRenderOptions.defaults()
            val render = Config().toConfig("config")
                    .root().render(defaults)
            Files.write(configFile, render.toByteArray())
            logger.info("Created Config at $configFile")
        }

        fun initOS(): OS {
            val operSys = System.getProperty("os.name").toLowerCase()
            if (operSys.contains("win")) {
                os = OS.WINDOWS
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                os = OS.LINUX
            } else if (operSys.contains("mac")) {
                os = OS.MAC
            } else {
                throw UnsupportedOperationException("OS Could not be Identified to calculate location for Property Saving!")
            }
            return os
        }
    }
}