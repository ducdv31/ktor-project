package com.dv.db

import com.dv.db.entity.UserTable
import com.dv.utils.JDBC_DRIVER
import com.dv.utils.TRANSACTION_REPEATABLE_READ
import com.dv.utils.note_db_url
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConnection {

    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(UserTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl = note_db_url
            driverClassName = JDBC_DRIVER
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = TRANSACTION_REPEATABLE_READ
            validate()
        }
        return HikariDataSource(config)
    }
}