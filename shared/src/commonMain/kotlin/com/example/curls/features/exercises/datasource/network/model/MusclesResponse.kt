package com.example.curls.features.exercises.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusclesResponse(
    @SerialName("results")
    val results: List<MuscleRemote>? = null,
)
