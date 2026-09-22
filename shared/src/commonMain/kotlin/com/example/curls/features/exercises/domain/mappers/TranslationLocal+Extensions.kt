package com.example.curls.features.exercises.domain.mappers

import com.example.curls.features.exercises.domain.Translation
import com.example.curls.cache.Translation as TranslationLocal

fun TranslationLocal.toTranslationDomain() = Translation(
    name = name,
    description = description,
)