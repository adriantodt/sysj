package net.adriantodt.sysj

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("net.adriantodt.sysj")
		.start()
}

