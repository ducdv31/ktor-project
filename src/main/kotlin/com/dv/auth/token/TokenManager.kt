package com.dv.auth.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.dv.plugins.login.model.UserCredential
import com.dv.utils.USERNAME
import io.ktor.config.*
import org.junit.rules.Verifier
import java.util.*

class TokenManager(config: ApplicationConfig) {
    private val audience = config.property("jwt.audience").getString()
    private val issuer = config.property("jwt.domain").getString()
    private val secret = config.property("jwt.secret").getString()
    private val expiredDate = System.currentTimeMillis() + 60000 * 60 * 24

    fun generateToken(userCredential: UserCredential): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(USERNAME, userCredential.username)
            .withExpiresAt(Date(expiredDate))
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifierToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }
}