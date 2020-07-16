package de.serverfrog.froglib

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("de.serverfrog.froglib")
		.start()
}

