package de.serverfrog.froglib.config

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DatasourceTest {

    @Test
    fun `Test getDatasource for correct JDBC URL`() {
        val jdbcUrl = Datasource.getJDBCUrl()
        assertTrue(jdbcUrl.endsWith("database.h2"))
        assertTrue(jdbcUrl.startsWith("jdbc:h2:file:"))

    }
}