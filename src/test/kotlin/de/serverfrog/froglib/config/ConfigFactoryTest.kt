package de.serverfrog.froglib.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ConfigFactoryTest {


    @Test
    fun `Test getConfig`() {
        val config = ConfigFactory.getConfig()
        assertNotNull(config)
    }

    @Test
    fun `Test save`() {
        var config = ConfigFactory.getConfig()
        assertNotNull(config)

        ConfigFactory.save(config)


        config = ConfigFactory.getConfig()
        assertNotNull(config)

    }
}