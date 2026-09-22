package com.example.curls.features.exercises.domain.mappers

import com.example.curls.features.exercises.domain.Muscle
import com.example.curls.cache.Muscle as MuscleLocal

fun MuscleLocal.toMuscleDomain() = Muscle(
    name = name,
    nameEn = nameEn,
    imageUrlMain = imageUrlMain,
    imageUrlSecondary = imageUrlSecondary,
)