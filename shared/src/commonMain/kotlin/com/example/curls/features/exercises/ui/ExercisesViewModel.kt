package com.example.curls.features.exercises.ui

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curls.features.exercises.datasource.ExercisesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExercisesViewModel(private val repository: ExercisesRepository) : ViewModel() {
    val state = ExercisesScreenState()
    private val selectedCategoriesFlow = snapshotFlow { state.selectedCategories.value }

    private val exercisesFlow = repository.exercisesFlow
        .combine(selectedCategoriesFlow) { exercises, categories ->
            exercises.filter { categories.isEmpty() || it.category in categories }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    init {
        initRepository()
        observeExercises()
        observeCategories()
    }

    private fun initRepository() = viewModelScope.launch {
        repository.initialize()
    }

    private fun observeExercises() = viewModelScope.launch {
        exercisesFlow.collectLatest { state.exercises.value = it }
    }

    private fun observeCategories() = viewModelScope.launch {
        repository.getCategoriesFlow().collectLatest {
            state.categories.value = it
        }
    }
}