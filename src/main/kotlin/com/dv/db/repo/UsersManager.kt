package com.dv.db.repo

import com.dv.db.entity.UserTable
import com.dv.plugins.login.model.User
import com.dv.plugins.login.model.UserCredential
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

object UsersManager {

    fun addUser(userCredential: UserCredential) {
        transaction {
            UserTable.insert {
                it[username] = userCredential.username
                it[password] = userCredential.getHashPass()
            }
        }
    }

    fun hadUser(userCredential: UserCredential): Boolean {
        var had = false
        transaction {
            val user = UserTable.select {
                UserTable.username eq userCredential.username
            }.singleOrNull()
            had = user != null
        }
        return had
    }

    fun findUserByName(username: String): User? {
        var user: User? = null
        transaction {
            user = UserTable.select {
                UserTable.username eq username
            }.map {
                val username = it[UserTable.username]
                val password = it[UserTable.password]
                User(username, password)
            }.singleOrNull()
        }
        return user
    }
}