package de.serverfrog.froglib.config

import java.nio.file.Path
import java.nio.file.Paths


data class Config(val applicationName: String = "3D Frog Lib",
                  var saveLocation: Path = Paths.get("./").toAbsolutePath()) {
}