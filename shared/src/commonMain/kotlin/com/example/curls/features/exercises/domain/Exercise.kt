package com.example.curls.features.exercises.domain

data class Exercise(
    val id: Long,
    val category: Category,
    val translation: Translation,
    val images: List<Image>,
    val videos: List<Video>,
    val muscles: List<Muscle>,
    val secondaryMuscles: List<Muscle>,
    val equipment: List<Equipment>,
)
