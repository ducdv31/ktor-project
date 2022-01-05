package com.dv.plugins.login.model

import org.mindrot.jbcrypt.BCrypt

data class UserCredential(
    var username: String,
    var password: String
) {
    fun getHashPass(): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun isInvalidateUser(): Boolean {
        return username.length >= 2 && password.length >= 6
    }
}
