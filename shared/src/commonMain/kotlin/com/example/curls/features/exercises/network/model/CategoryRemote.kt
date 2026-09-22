package com.example.curls.features.exercises.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRemote(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
)