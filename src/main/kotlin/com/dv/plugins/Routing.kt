package com.dv.plugins

import com.dv.auth.token.TokenManager
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*

fun Application.configureRouting() {

    routing {
        post("/login") {
            val tokenManager = TokenManager(environment.config)
            val token = tokenManager.generateToken()
            call.respond(token)
        }

        authenticate {
            get("/") {
                call.respondText("Hello World!")
            }
        }
    }
}
