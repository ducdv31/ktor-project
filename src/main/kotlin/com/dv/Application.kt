package com.dv

import com.dv.db.DatabaseConnection
import io.ktor.application.*
import com.dv.plugins.*
import com.dv.plugins.login.userRegistration

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureSerialization()
    /* config database */
    DatabaseConnection.init()
    /* routing */
    userRegistration()
    configureRouting()
}
