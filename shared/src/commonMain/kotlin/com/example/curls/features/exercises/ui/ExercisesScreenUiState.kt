package com.example.curls.features.exercises.ui

import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.features.exercises.datasource.domain.Equipment
import com.example.curls.features.exercises.datasource.domain.Exercise

data class ExercisesScreenUiState(
    val exercises: List<Exercise> = emptyList(),
    val availableCategories: List<Category> = emptyList(),
    val selectedCategories: List<Category> = emptyList(),
    val availableEquipment: List<Equipment> = emptyList(),
    val selectedEquipment: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
