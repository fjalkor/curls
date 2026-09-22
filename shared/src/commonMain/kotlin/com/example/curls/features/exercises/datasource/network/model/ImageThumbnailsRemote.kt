package com.example.curls.features.exercises.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageThumbnailsRemote(
    @SerialName("small")
    val small: String? = null,
    @SerialName("medium")
    val medium: String? = null,
)