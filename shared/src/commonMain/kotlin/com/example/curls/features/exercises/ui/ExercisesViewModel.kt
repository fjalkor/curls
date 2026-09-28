package com.example.curls.features.exercises.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curls.features.exercises.datasource.ExercisesRepository
import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.features.exercises.datasource.domain.Equipment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
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
import org.koin.mp.KoinPlatform

class ExercisesViewModel(private val repository: ExercisesRepository) : ViewModel() {

    val uiState: StateFlow<ExercisesScreenUiState>
        field = MutableStateFlow(ExercisesScreenUiState())

    private val observerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val exercisesFlow = combine(
        repository.exercisesFlow,
        uiState.map { it.selectedCategories }.distinctUntilChanged(),
        uiState.map { it.selectedEquipment }.distinctUntilChanged(),
    ) { exercises, selectedCategories, selectedEquipment ->
        exercises
            .filter { it.category in selectedCategories }
            .filter { it.equipment.all { equipment -> equipment in selectedEquipment } }
    }.stateIn(
        scope = observerScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList(),
    )

    init {
        initRepository()
        observeExercises()
        observeCategories()
        observeEquipment()

        viewModelScope.coroutineContext[Job]?.invokeOnCompletion {
            println("kotlin: viewModelScope completed/cancelled! cause: $it")
        }
    }

    fun updatePage(newPage: Int) = uiState.update {
        it.copy(currentPage = newPage)
    }

    fun onEquipmentTapped(equipment: Equipment) = uiState.update { state ->
        state.copy(
            selectedEquipment = if (equipment in state.selectedEquipment) {
                state.selectedEquipment.filter { it != equipment }
            } else {
                state.selectedEquipment + equipment
            }
        )
    }

    fun onCategoryTapped(category: Category) = uiState.update { state ->
        state.copy(
            selectedCategories = if (category in state.selectedCategories) {
                state.selectedCategories.filter { it != category }
            } else {
                state.selectedCategories + category
            }
        )
    }

    private fun initRepository() = observerScope.launch {
        repository.initialize()
    }

    private fun observeExercises() = observerScope.launch {
        exercisesFlow.collectLatest { exercises ->
            uiState.update { it.copy(exercises = exercises) }
        }
    }

    private fun observeCategories() = observerScope.launch {
        repository.getCategoriesFlow().collectLatest { categories ->
            uiState.update { it.copy(availableCategories = categories) }
        }
    }

    private fun observeEquipment() = observerScope.launch {
        repository.getEquipmentFlow().collectLatest { equipment ->
            uiState.update { it.copy(availableEquipment = equipment) }
        }
    }

    fun observeState(onChange: (ExercisesScreenUiState) -> Unit) = observerScope.launch {
        uiState.collectLatest { onChange(it) }
    }
}

object KoinHelper {
    fun getExercisesViewModel(): ExercisesViewModel = KoinPlatform.getKoin().get()
}