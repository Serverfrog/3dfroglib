package de.serverfrog.froglib.config

import io.micronaut.context.env.PropertySource


class Datasource {

    companion object{
        val datasourcePrefix = "datasources.default"
        val driverClassName = "org.h2.Driver"
        val username = "sa"
        val password = ""
        val schemaGenerate = "CREATE"
        val dialect = "H2"

        fun getDatasourceProperty(): PropertySource? {
            val databasePath = ConfigFactory.getUserConfigPath()
                    .resolve("database.h2")
                    .toUri().toString()
                    .substring(8) // remove file:////
            val databaseUrl = "jdbc:h2:file:$databasePath"

            return PropertySource.of(
                    mapOf(
                        "$datasourcePrefix.url" to databaseUrl,
                        "$datasourcePrefix.driverClassName" to driverClassName,
                        "$datasourcePrefix.username" to username,
                        "$datasourcePrefix.password" to password,
                        "$datasourcePrefix.schemaGenerate" to schemaGenerate,
                        "$datasourcePrefix.dialect" to dialect
                    )
            )

        }
    }
}