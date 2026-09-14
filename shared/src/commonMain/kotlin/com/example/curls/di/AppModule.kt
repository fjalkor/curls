package com.example.curls.di

import com.example.curls.MainViewModel
import com.example.curls.Repository
import com.example.curls.network.Api
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    single<Api> { Api() }
    single<Repository> { Repository(get(), get()) }
    factory { MainViewModel(get()) }
}
