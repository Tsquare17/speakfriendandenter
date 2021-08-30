package com.tsquare.speakfriend.database.schema

import com.tsquare.speakfriend.database.connection.Conn
import com.tsquare.speakfriend.database.tables.Users
import com.tsquare.speakfriend.database.tables.Accounts
import com.tsquare.speakfriend.database.tables.Settings
import com.tsquare.speakfriend.database.tables.SystemSettings
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class Schema {
    init {
        Conn()
    }

    fun up() {
        transaction {
            SchemaUtils.create(SystemSettings, Users, Accounts, Settings)
        }
    }

    fun down() {
        transaction {
            SchemaUtils.drop(SystemSettings, Users, Accounts, Settings)
        }
    }
}
