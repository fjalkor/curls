package com.example.curls.cache

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidDatabaseDriverFactory(private val context: Context): DatabaseDriverFactory {
    override fun createDriver(): SqlDriver = AndroidSqliteDriver(
        schema = AppDatabase.Schema,
        context = context,
        name = "curls_app.db",
        callback = object : AndroidSqliteDriver.Callback(AppDatabase.Schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.execSQL("PRAGMA foreign_keys=ON;")
            }
        }
    )
}