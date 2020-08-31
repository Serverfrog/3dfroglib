package de.serverfrog.froglib.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import tornadofx.*
import kotlin.properties.ObservableProperty

object  Models: IntIdTable() {
    val name: Column<String> = varchar("name", 255)
    val description: Column<String> = varchar("description", 5000)

}

class Model(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Model>(Models)
    var name: String by Models.name
    var description: String by Models.description
    val files by File referrersOn Files.model

}