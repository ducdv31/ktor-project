package com.dv.plugins

import com.dv.auth.token.TokenManager
import com.dv.utils.LOGIN
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*

fun Application.configureRouting() {

    routing {
        authenticate {
            get("/") {
                call.respondText("Hello World!")
            }
        }
    }
}
