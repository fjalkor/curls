package com.example.curls.debug

import androidx.compose.runtime.MutableState
import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.features.exercises.datasource.domain.Equipment
import com.example.curls.features.exercises.datasource.domain.Exercise
import com.example.curls.features.exercises.datasource.domain.Muscle
import com.example.curls.features.exercises.datasource.domain.Named
import com.example.curls.features.exercises.datasource.domain.Translation
import com.example.curls.features.exercises.ui.ExercisesScreenUiState

object PreviewHelper {
    val debugEquipment = listOf(
        Equipment("Barbell"),
        Equipment("Bench"),
        Equipment("Cable machine"),
        Equipment("Dumbbell"),
        Equipment("Gym mat"),
        Equipment("Incline bench"),
        Equipment("Kettlebell"),
        Equipment("Pull-up bar"),
        Equipment("Resistance band"),
        Equipment("SZ-Bar"),
        Equipment("Swiss Ball"),
        Equipment("none (bodyweight exercise)"),
    )

    val debugCategories = listOf(
        Category(10L, "Abs"),
        Category(8L, "Arms"),
        Category(12L, "Back"),
        Category(14L, "Calves"),
        Category(15L, "Cardio"),
        Category(11L, "Chest"),
        Category(9L, "Legs"),
        Category(13L, "Shoulders"),
    )

    val debugTranslation = Translation(
        name = "Crunches",
        description = "<p>Some fancy description of this abs exercise</p>",
    )

    val debugMuscle = Muscle(
        name = "Rectus abdominis",
        nameEn = "Abs",
        imageUrlMain = "https://wger.de/static/images/muscles/main/muscle-6.592f938fa8c7.svg",
        imageUrlSecondary = "https://wger.de/static/images/muscles/secondary/muscle-6.370f77c2860e.svg",
    )

    val debugExercise = Exercise(
        id = 0L,
        category = debugCategories.first(),
        translation = debugTranslation,
        images = emptyList(),
        videos = emptyList(),
        muscles = listOf(debugMuscle),
        secondaryMuscles = listOf(debugMuscle),
        equipment = listOf(debugEquipment.first()),
    )

    fun <T : Named> onItemTapped(item: T, state: MutableState<ExercisesScreenUiState>) {
        when (item) {
            is Equipment -> state.value = state.value.copy(
                selectedEquipment = if (item in state.value.selectedEquipment) {
                    state.value.selectedEquipment.filter { equipment -> equipment != item }
                } else {
                    state.value.selectedEquipment + item
                }
            )

            is Category -> state.value = state.value.copy(
                selectedCategories = if (item in state.value.selectedCategories) {
                    state.value.selectedCategories.filter { equipment -> equipment != item }
                } else {
                    state.value.selectedCategories + item
                }
            )
        }
    }
}