package de.serverfrog.froglib.config



class Datasource {

    companion object{
        val datasourcePrefix = "datasources.default"
        val driverClassName = "org.h2.Driver"
        val username = "sa"
        val password = ""
        val schemaGenerate = "CREATE"
        val dialect = "H2"


        fun getJDBCUrl(): String {
            val databasePath = ConfigFactory.getUserConfigPath()
                    .resolve("database.h2")
                    .toUri().toString()
                    .substring(8) // remove file:////
            return "jdbc:h2:file:$databasePath"
        }
    }
}