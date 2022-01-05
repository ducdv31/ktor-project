package com.dv.db.entity

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
    val username = varchar("username", 512)
    val password = varchar("password", 512)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(username)
}