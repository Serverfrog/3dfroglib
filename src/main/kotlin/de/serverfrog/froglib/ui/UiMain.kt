package de.serverfrog.froglib.ui

import de.serverfrog.froglib.config.UserProperties
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.scheduling.annotation.Async
import javafx.application.Application
import javafx.application.Platform
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UiMain {

    val logger: Logger = LoggerFactory.getLogger(UiMain::class.java)

    @Inject
    lateinit var properties: UserProperties

    @EventListener
    @Async
    internal open fun onStartupEvent(event: StartupEvent) {

        startupEvent(event)
    }

    fun startupEvent(event: StartupEvent?) {

        println("Event Source=${event?.source}")
        logger.info("Startup Finished, Starting UI  $properties")
        Application.launch(MainApp::class.java)

    }
}