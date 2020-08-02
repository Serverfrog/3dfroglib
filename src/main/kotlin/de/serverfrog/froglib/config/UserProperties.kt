package de.serverfrog.froglib.config

import com.typesafe.config.ConfigFactory
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Requires
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserProperties() {

    val logger: Logger = LoggerFactory.getLogger(UserProperties::class.java)
    @Inject
    lateinit var config: Config

    @PostConstruct
    fun onPostConstruct(){
        println("config")
        logger.info("Config Inits. $config")
    }

}