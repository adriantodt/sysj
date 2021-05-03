package net.adriantodt.sysj.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post

@Controller("/auth")
class AuthenticationController {
    @Post("/signIn")
    fun signIn() {

    }

    @Post("/signOut")
    fun signOut(@Header("Authorization") refreshToken: String) {

    }

    @Get("/userInfo")
    fun userInfo(@Header("Authorization") accessToken: String) {

    }

    @Get("/refreshToken")
    fun refreshToken(@Header("Authorization") accessToken: String) {

    }
}
