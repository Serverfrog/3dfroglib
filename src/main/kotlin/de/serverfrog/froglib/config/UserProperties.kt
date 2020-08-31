package de.serverfrog.froglib.config

import java.nio.file.Path


class UserProperties(config: Config) {

    companion object {
        val userProperties: UserProperties = UserProperties(ConfigFactory.getConfig())

    }
}