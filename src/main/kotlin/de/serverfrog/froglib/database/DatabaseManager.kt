package de.serverfrog.froglib.database

import de.serverfrog.froglib.config.Datasource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager {

    val db by lazy {
        Database.connect(Datasource.getJDBCUrl())
    }


    fun startup() {
        println("Database URL is ${db.url}")
        transaction {
            SchemaUtils.create(Models, Files)
        }
    }

    fun getModels(): List<Model> {


        val models = mutableListOf<Model>()
        transaction {
            Model.all().forUpdate().forEach { t: Model -> t.delete() }

            for (i in 0..20) {
                val model = Model.new {
                    name = "Model $i"
                    description = "Description of $i"
                }
                models.add(model)
            }
        }

        return models
    }

}