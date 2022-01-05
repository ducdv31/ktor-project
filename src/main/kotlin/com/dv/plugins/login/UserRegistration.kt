package com.dv.plugins.login

import com.dv.auth.token.TokenManager
import com.dv.common.ResponseData
import com.dv.db.entity.UserTable
import com.dv.db.repo.UsersManager
import com.dv.plugins.login.model.User
import com.dv.plugins.login.model.UserCredential
import com.dv.utils.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

fun Application.userRegistration() {
    routing {
        /* login */
        post(LOGIN) {
            val userCredential = try {
                call.receive<UserCredential>()
            } catch (e: Exception) {
                call.respond(
                    ResponseData(
                        code = 0,
                        data = "Invalid user or password ${e.message}"
                    )
                )
                return@post
            }
            val userDb = transaction {
                UserTable.select {
                    UserTable.username eq userCredential.username
                }.map {
                    val username = it[UserTable.username]
                    val password = it[UserTable.password]
                    User(username, password)
                }.singleOrNull()
            }
            if (userDb == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    ResponseData(
                        code = 0,
                        data = "User is not exist"
                    )
                )
                return@post
            }

            if (!BCrypt.checkpw(userCredential.password, userDb.password)) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    ResponseData(
                        code = 0,
                        data = "Password error"
                    )
                )
                return@post
            }

            val tokenManager = TokenManager(environment.config)
            val token = tokenManager.generateToken(userCredential)
            call.respond(ResponseData(data = token))
        }

        /* register */
        post(REGISTER) {
            val userCredential = call.receive<UserCredential>()
            if (userCredential.isInvalidateUser() && !UsersManager.hadUser(userCredential)) {
                UsersManager.addUser(userCredential)
                call.respond(
                    status = HttpStatusCode.Created,
                    ResponseData(data = "Created user")
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    ResponseData(
                        code = 0,
                        data = "User is not valid"
                    )
                )
            }
        }

        /* change password */
        authenticate {
            post(UPDATE_PASSWORD) {
            }
        }

        /* Delete user */
        authenticate {
            post(UN_REGISTER) {
            }
        }
    }
}