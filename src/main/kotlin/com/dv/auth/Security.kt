package com.dv.plugins

import io.ktor.auth.*
import io.ktor.auth.jwt.*
import com.dv.auth.token.TokenManager
import io.ktor.application.*

fun Application.configureSecurity() {

    authentication {
        jwt {
            val tokenManager = TokenManager(environment.config)
            realm = environment.config.property("jwt.realm").getString()
            verifier(tokenManager.verifierToken())
            validate { credential ->
                if (credential.payload.audience.contains(environment.config.property("jwt.audience").getString())) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

}
