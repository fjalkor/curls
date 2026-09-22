package com.example.curls.features.exercises.datasource.network.mappers

import com.example.curls.cache.Translation
import com.example.curls.features.exercises.datasource.network.model.TranslationRemote

fun TranslationRemote.toTranslationLocal(): Translation? = Translation(
    id = id?.toLong() ?: return null,
    name = name?.takeIf { it.isNotBlank() } ?: return null,
    exerciseId = exerciseId?.toLong() ?: return null,
    description = description,
    languageId = languageId?.toLong() ?: return null,
)