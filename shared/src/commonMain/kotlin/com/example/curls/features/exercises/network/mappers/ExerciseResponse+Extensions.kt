package com.example.curls.features.exercises.network.mappers

import com.example.curls.features.exercises.network.model.ExerciseRemote

@Throws(Exception::class)
fun ExerciseRemote.toExerciseDetailedLocal(): ExerciseDetailedLocal = ExerciseDetailedLocal(
    id = id?.toLong() ?: throw RuntimeException("cannot map id: $id"),
    categoryId = category?.id?.toLong() ?: throw RuntimeException("id: $id cannot map categoryId: ${category?.id}"),
    translations = translations
        ?.mapNotNull { it.toTranslationLocal() }
        ?.takeIf { it.isNotEmpty() }
        ?: throw RuntimeException("id: $id cannot map translations: ${translations}"),
    images = images?.mapNotNull { it.toImageLocal() }.orEmpty(),
    videos = videos?.mapNotNull { it.toVideoLocal() }.orEmpty(),
    muscleIds = muscles?.mapNotNull { it.id?.toLong() }.orEmpty(),
    secondaryMuscleIds = musclesSecondary?.mapNotNull { it.id?.toLong() }.orEmpty(),
    equipmentIds = equipment?.mapNotNull { it.id?.toLong() }.orEmpty(),
)
