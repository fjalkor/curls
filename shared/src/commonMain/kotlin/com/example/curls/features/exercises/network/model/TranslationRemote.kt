package com.example.curls.features.exercises.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationRemote(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("exercise")
    val exerciseId: Int? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("language")
    val languageId: Int? = null,
)