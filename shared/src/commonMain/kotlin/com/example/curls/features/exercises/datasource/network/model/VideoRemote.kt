package com.example.curls.features.exercises.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoRemote(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("exercise")
    val exerciseId: Int? = null,
    @SerialName("video")
    val videoUrl: String? = null,
    @SerialName("size")
    val size: Long? = null,
    @SerialName("duration")
    val duration: String? = null,
    @SerialName("width")
    val width: Int? = null,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("codec")
    val codec: String? = null,
    @SerialName("codecLong")
    val codecLong: String? = null,
)