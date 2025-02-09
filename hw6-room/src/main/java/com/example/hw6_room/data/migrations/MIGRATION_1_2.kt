package com.example.hw6_room.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL(
            "UPDATE transactions SET date = DATE('now') WHERE date = ''"
        )

        database.execSQL(
            """
            UPDATE transactions 
            SET id = (SELECT MAX(id) + 1 FROM transactions) 
            WHERE id = 0
            """
        )
    }
}