package de.serverfrog.froglib.ui

import de.serverfrog.froglib.database.DatabaseManager
import javafx.stage.Stage
import tornadofx.*

class MainApp : App() {

    companion object {
        fun main(args: Array<String>) {
            launch<MainApp>(args)
        }
    }

    override val primaryView = MainView::class


    val databaseManager = DatabaseManager()

    override fun start(stage: Stage) {
        super.start(stage)
        databaseManager.startup()

        stage.width = 800.0
        stage.height = 600.0
        stage.titleProperty().unbind()
        stage.titleProperty().set("3D FrogLib")
        val primaryViewInstance = find(primaryView)
        primaryViewInstance.models.clear()
        primaryViewInstance.models.addAll(databaseManager.getModels())
    }

    override fun stop() {
        super.stop()
    }
}