package de.serverfrog.froglib.ui

import javafx.stage.Stage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.framework.junit5.ApplicationExtension

@ExtendWith(ApplicationExtension::class)
internal class MainAppTest {

    lateinit var mainApp: MainApp

    /**
     * Will be called with `@Before` semantics, i. e. before each test method.
     */
    fun start(stage: Stage) {
        mainApp = MainApp()
        mainApp.start(stage)
    }

    @Test
    fun `startup Works`() {
    }

}