package com.example.curls.network

import com.example.curls.network.model.MuscleGroupResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Api {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    @Throws(Exception::class)
    suspend fun getAllMuscleGroups(): MuscleGroupResponse =
        httpClient.get("https://wger.de/api/v2/exercisecategory/").body()
}