package com.dv.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.transaction

fun <T> queryDb(block: suspend CoroutineScope.() -> T) {
    transaction {
        CoroutineScope(Dispatchers.Default).launch {
            block()
        }
    }
}