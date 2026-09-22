package com.example.curls.features.exercises.datasource.network.mappers

import com.example.curls.cache.Video
import com.example.curls.features.exercises.datasource.network.model.VideoRemote

fun VideoRemote.toVideoLocal(): Video? = Video(
    id = id?.toLong() ?: return null,
    exerciseId = exerciseId?.toLong() ?: return null,
    videoUrl = videoUrl?.takeIf { it.isNotBlank() } ?: return null,
    size = size ?: return null,
    duration = duration,
    width = width?.toLong() ?: return null,
    height = height?.toLong() ?: return null,
    codec = codec,
    codecLong = codecLong,
)