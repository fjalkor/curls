package com.example.curls.features.exercises.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
    @SerialName("results")
    val results: List<CategoryRemote>? = null,
)