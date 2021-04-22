package net.adriantodt.sysj.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KLogging

@Controller("/hello") 
class HelloController {
    companion object : KLogging()

    @Get(produces = [MediaType.TEXT_PLAIN]) 
    fun index(): String {
        return "Hello World" 
    }
}
