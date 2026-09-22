package com.example.curls.features.exercises.datasource.domain.mappers

import com.example.curls.features.exercises.datasource.domain.Image
import com.example.curls.cache.Image as ImageLocal

fun ImageLocal.toImageDomain() = Image(
    url = imageUrl,
    thumbnailSmall = thumbnailSmall,
    thumbnailMedium = thumbnailMedium,
)