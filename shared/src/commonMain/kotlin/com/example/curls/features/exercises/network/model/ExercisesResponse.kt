package com.example.curls.features.exercises.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExercisesResponse(
    @SerialName("count")
    val count: Int? = null,
    @SerialName("next")
    val next: String? = null,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("results")
    val results: List<ExerciseRemote>? = null,
)

