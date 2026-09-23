package com.example.curls.features.exercises.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curls.features.exercises.datasource.ExercisesRepository
import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.features.exercises.datasource.domain.Equipment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExercisesViewModel(private val repository: ExercisesRepository) : ViewModel() {

    val uiState: StateFlow<ExercisesScreenUiState>
    field = MutableStateFlow(ExercisesScreenUiState())

    private val exercisesFlow = combine(
        repository.exercisesFlow,
        uiState.map { it.selectedCategories }.distinctUntilChanged(),
        uiState.map { it.selectedEquipment }.distinctUntilChanged(),
        ) { exercises, selectedCategories, selectedEquipment ->
            exercises
                .filter { it.category in selectedCategories }
                .filter { it.equipment.all { equipment -> equipment in selectedEquipment } }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    init {
        initRepository()
        observeExercises()
        observeCategories()
        observeEquipment()
    }

    fun selectEquipment(equipment: List<Equipment>) = uiState.update {
        it.copy(selectedEquipment = equipment)
    }

    fun selectCategories(categories: List<Category>) = uiState.update {
        it.copy(selectedCategories = categories)
    }

    private fun initRepository() = viewModelScope.launch {
        repository.initialize()
    }

    private fun observeExercises() = viewModelScope.launch {
        exercisesFlow.collectLatest { exercises ->
            uiState.update { it.copy(exercises = exercises) }
        }
    }

    private fun observeCategories() = viewModelScope.launch {
        repository.getCategoriesFlow().collectLatest { categories ->
            uiState.update { it.copy(availableCategories = categories) }
        }
    }

    private fun observeEquipment() = viewModelScope.launch {
        repository.getEquipmentFlow().collectLatest { equipment ->
            uiState.update { it.copy(availableEquipment = equipment) }
        }
    }
}