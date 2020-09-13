package de.serverfrog.froglib.ui

import de.serverfrog.froglib.util.ThingiverseUtil
import javafx.scene.control.TextField
import tornadofx.*

class ImportThingiverseMenu : View("My View") {
    override val root = vbox {}

    var thingiverseLink: TextField by singleAssign()
    val status: TaskStatus by inject()

    init {

        with(root) {
            label("Save Location")
            thingiverseLink = textfield("https://www.thingiverse.com/thing:3915647") {
                useMaxWidth = true
            }
            button("Save") {
                useMaxWidth = true
                action {
                    ThingiverseUtil.downloadAndSave(thingiverseLink.text, status) { close() }
                }
            }
            hbox(4.0) {
                progressbar(status.progress)
                label(status.message)
                paddingAll = 4
            }
        }
    }
}
