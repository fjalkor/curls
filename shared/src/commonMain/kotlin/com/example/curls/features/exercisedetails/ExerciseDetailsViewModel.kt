package com.example.curls.features.exercisedetails

import androidx.lifecycle.ViewModel
import com.example.curls.features.exercises.datasource.ExercisesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ExerciseDetailsViewModel(
    private val exerciseId: Long,
    private val exercisesRepository: ExercisesRepository,
) : ViewModel() {
    private val observerScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    val exercise = exercisesRepository.exercisesFlow
        .map { allExercises -> allExercises.firstOrNull { it.id == exerciseId } }
        .stateIn(
            scope = observerScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5000),
        )
}