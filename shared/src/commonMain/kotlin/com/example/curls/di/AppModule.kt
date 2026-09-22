package com.example.curls.di

import com.example.curls.cache.Database
import com.example.curls.features.exercises.ExercisesRepository
import com.example.curls.features.exercises.ExercisesViewModel
import com.example.curls.features.exercises.network.ExercisesApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    single<HttpClient> { HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }}
    single<ExercisesApi> { ExercisesApi(get()) }
    single<Database> { Database(get()) }
    single<ExercisesRepository> { ExercisesRepository(get(), get()) }
    factory { ExercisesViewModel(get()) }
}
