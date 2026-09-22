package com.example.curls.features.exercises.datasource.domain.mappers

import com.example.curls.features.exercises.datasource.domain.Translation
import com.example.curls.cache.Translation as TranslationLocal

fun TranslationLocal.toTranslationDomain() = Translation(
    name = name,
    description = description,
)