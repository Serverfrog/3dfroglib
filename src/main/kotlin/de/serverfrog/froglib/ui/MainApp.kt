package de.serverfrog.froglib.ui

import javafx.application.Application
import javafx.stage.Stage
import tornadofx.App

class MainApp : App(MainView::class) {

    override fun start(stage: Stage) {
        super.start(stage)
        println("Start this shit")
    }

    override fun stop() {
        super.stop()
    }
}