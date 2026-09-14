package com.example.curls.di

import com.example.curls.cache.DatabaseDriverFactory
import com.example.curls.cache.IOSDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DatabaseDriverFactory> { IOSDatabaseDriverFactory() }
}