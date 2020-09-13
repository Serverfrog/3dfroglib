package de.serverfrog.froglib.util

import de.serverfrog.froglib.ui.MainApp
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start
import tornadofx.*

@ExtendWith(ApplicationExtension::class, MockitoExtension::class)
internal class ThingiverseUtilTest {
    lateinit var mainApp: MainApp

    lateinit var status: TaskStatus

    /**
     * Will be called with `@Before` semantics, i. e. before each test method.
     */
    @Start
    fun start(stage: Stage) {
        status = TaskStatus()

        println("Mocked= $status")
        mainApp = MainApp()
        mainApp.start(stage)

    }

    @Test
    fun `test Thingiverse Downlad`() {
        var completed = false
        ThingiverseUtil.downloadAndSave("https://www.thingiverse.com/thing:3915647", status) { completed = true }
        var i = 0

        while (!completed && i < 500) {
            Thread.sleep(100)
            i++
            if (i % 50 == 0) {
                println("Message = ${status.message.get()}")
            }
        }
        assertTrue(completed, "should be completed")
    }
}