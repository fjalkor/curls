package com.example.curls.features.exercises.datasource.network

import com.example.curls.features.exercises.datasource.network.model.CategoriesResponse
import com.example.curls.features.exercises.datasource.network.model.EquipmentResponse
import com.example.curls.features.exercises.datasource.network.model.ExercisesResponse
import com.example.curls.features.exercises.datasource.network.model.MusclesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ExercisesApi(private val client: HttpClient) {
    @Throws(Exception::class)
    suspend fun getCategories(): com.example.curls.features.exercises.datasource.network.model.CategoriesResponse =
        client.get("https://wger.de/api/v2/exercisecategory/").body()

    @Throws(Exception::class)
    suspend fun getMuscles(): com.example.curls.features.exercises.datasource.network.model.MusclesResponse =
        client.get("https://wger.de/api/v2/muscle/").body()

    @Throws(Exception::class)
    suspend fun getEquipment(): com.example.curls.features.exercises.datasource.network.model.EquipmentResponse =
        client.get("https://wger.de/api/v2/equipment/").body()

    @Throws(Exception::class)
    suspend fun getExercises(limit: Int = 20, offset: Int = 0): com.example.curls.features.exercises.datasource.network.model.ExercisesResponse =
        client.get("https://wger.de/api/v2/exerciseinfo/?language=2&limit=${limit}&offset=${offset}").body()
}
