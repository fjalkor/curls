package com.example.curls.features.exercises.domain.mappers

import com.example.curls.features.exercises.domain.Image
import com.example.curls.cache.Image as ImageLocal

fun ImageLocal.toImageDomain() = Image(
    url = imageUrl,
    thumbnailSmall = thumbnailSmall,
    thumbnailMedium = thumbnailMedium,
)