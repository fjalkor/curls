package com.example.curls.features.exercises

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.curls.features.exercises.domain.Category
import com.example.curls.features.exercises.domain.Exercise
import kotlin.collections.plus

class ExercisesScreenState(
    val exercises: MutableState<List<Exercise>> = mutableStateOf(emptyList()),
    val selectedCategories: MutableState<List<Category>> = mutableStateOf(emptyList()),
    val categories: MutableState<List<Category>> = mutableStateOf(emptyList()),
) {
    fun onClick(category: Category) {
        val isCategorySelected = category in selectedCategories.value
        if (isCategorySelected) {
            selectedCategories.value = selectedCategories.value.filter { it != category }
        } else {
            selectedCategories.value += category
        }
    }
}