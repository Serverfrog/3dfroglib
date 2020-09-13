package de.serverfrog.froglib.database

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class DatabaseManagerTest {


    @Test
    fun `Testing Startup`() {
        val databaseManager = DatabaseManager()
        databaseManager.startup()
    }

    @Test
    fun `Testing getAllModels`() {
        val databaseManager = DatabaseManager()
        databaseManager.startup()
        val models = databaseManager.getModels()
        assertFalse(models.isEmpty())
    }
}