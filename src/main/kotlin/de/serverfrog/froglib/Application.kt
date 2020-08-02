package de.serverfrog.froglib

import de.serverfrog.froglib.config.Config
import de.serverfrog.froglib.config.ConfigFactory
import de.serverfrog.froglib.config.Datasource
import de.serverfrog.froglib.config.UserProperties
import de.serverfrog.froglib.ui.UiMain
import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    val mainpackage = "de.serverfrog.froglib"
    val application = build()
            .args(*args)
            .propertySources(Datasource.getDatasourceProperty())
            .packages("$mainpackage.config", "$mainpackage.ui", mainpackage)
            .singletons(ConfigFactory::class.java, Config::class.java, UserProperties::class.java)
            .eagerInitSingletons(true)
            .start()
    println(application.findBean(UiMain::class.java))

}

