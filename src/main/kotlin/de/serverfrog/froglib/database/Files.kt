package de.serverfrog.froglib.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Files: IntIdTable() {
    val filePath = varchar("filePath", 1024)
    val model = reference("model", Models)
}
class File(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<File>(Files)
    var name by Files.filePath
    var model by Model referencedOn Files.model
}