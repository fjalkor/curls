package com.example.curls.di

import com.example.curls.cache.AndroidDatabaseDriverFactory
import com.example.curls.cache.DatabaseDriverFactory
import org.koin.dsl.module

actual val platformModule = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(get()) }
}