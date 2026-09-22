package com.example.curls.features.exercises.network.mappers

import com.example.curls.cache.Muscle
import com.example.curls.features.exercises.network.model.MuscleRemote

fun MuscleRemote.toMuscleLocal(): Muscle? = Muscle(
    id = id?.toLong() ?: return null,
    name = name?.takeIf { it.isNotBlank() } ?: return null,
    nameEn = nameEN,
    isFront = isFront == true,
    imageUrlMain = imageUrlMain,
    imageUrlSecondary = imageUrlSecondary,
)