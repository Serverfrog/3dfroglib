package de.serverfrog.froglib.ui

import de.serverfrog.froglib.database.Model
import javafx.collections.ObservableList
import javafx.scene.layout.Priority
import tornadofx.*

class MainView : View() {

    val models: ObservableList<Model> = observableListOf()

    override val root = vbox {


        menubar {
            menu("File") {
                item("Settings").action {
                    openInternalWindow(SettingsMenu::class)
                }
            }
            menu("Import") {
                item("from Thingiverse").action {
                    openInternalWindow(ImportThingiverseMenu::class)
                }
            }
        }

        tableview<Model>(models) {
            readonlyColumn("Name", Model::name)
            readonlyColumn("Description", Model::description)
            smartResize()

            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS
        }


    }


}