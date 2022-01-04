package com.dv.plugins

import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
        }
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}
