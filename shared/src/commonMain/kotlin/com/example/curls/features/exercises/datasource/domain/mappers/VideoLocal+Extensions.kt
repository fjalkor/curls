package com.example.curls.features.exercises.datasource.domain.mappers

import com.example.curls.features.exercises.datasource.domain.Video
import com.example.curls.cache.Video as VideoLocal

fun VideoLocal.toVideoDomain() = Video(
    videoUrl = videoUrl,
    duration = duration,
    width = width,
    height = height,
)
