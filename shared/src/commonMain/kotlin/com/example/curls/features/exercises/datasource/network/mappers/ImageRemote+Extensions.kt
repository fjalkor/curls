package com.example.curls.features.exercises.datasource.network.mappers

import com.example.curls.cache.Image
import com.example.curls.features.exercises.datasource.network.model.ImageRemote

fun ImageRemote.toImageLocal(): Image? = Image(
    id = id?.toLong() ?: return null,
    exerciseId = exerciseId?.toLong() ?: return null,
    imageUrl = imageUrl?.takeIf { it.isNotBlank() } ?: return null,
    thumbnailSmall = thumbnails?.small,
    thumbnailMedium = thumbnails?.medium,
)