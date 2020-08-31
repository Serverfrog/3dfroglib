package de.serverfrog.froglib.ui

import de.serverfrog.froglib.config.ConfigFactory
import javafx.scene.Parent
import javafx.scene.control.TextField
import tornadofx.*
import java.nio.file.Paths

class SettingsMenu : View("Settings") {

    override val root = vbox {}

    var saveLocationField: TextField by singleAssign()

    init {
        val config = ConfigFactory.getConfig()

        with(root) {
            label("URL")
            saveLocationField = textfield("config.saveLocation.toString()") {
                useMaxWidth = true
            }
            button("Save") {
                useMaxWidth = true
                action {
                    var path = config.saveLocation
                    try{
                        path = Paths.get(saveLocationField.text)
                    }catch (e: Exception){
                        println("Error on Getting Path. $e")
                    }
                    config.saveLocation = path
                    ConfigFactory.save(config)
                    close()
                }
            }

        }
    }
}
