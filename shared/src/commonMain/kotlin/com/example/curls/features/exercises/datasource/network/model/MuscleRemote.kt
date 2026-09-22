package com.example.curls.features.exercises.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MuscleRemote(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("name_en")
    val nameEN: String? = null,
    @SerialName("is_front")
    val isFront: Boolean? = null,
    @SerialName("image_url_main")
    val imageUrlMain: String? = null,
    @SerialName("image_url_secondary")
    val imageUrlSecondary: String? = null,
)