package com.example.curls.features.exercises.network.mappers

import com.example.curls.cache.Image
import com.example.curls.cache.Translation
import com.example.curls.cache.Video

data class ExerciseDetailedLocal(
    val id: Long,
    val categoryId: Long,
    val translations: List<Translation>,
    val images: List<Image>,
    val videos: List<Video>,
    val muscleIds: List<Long>,
    val secondaryMuscleIds: List<Long>,
    val equipmentIds: List<Long>,
)