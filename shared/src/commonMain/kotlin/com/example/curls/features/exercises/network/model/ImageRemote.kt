package com.example.curls.features.exercises.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageRemote(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("exercise")
    val exerciseId: Int? = null,
    @SerialName("image")
    val imageUrl: String? = null,
    @SerialName("thumbnails")
    val thumbnails: ImageThumbnailsRemote? = null,
    @SerialName("is_main")
    val isMain: Boolean? = null,

    )