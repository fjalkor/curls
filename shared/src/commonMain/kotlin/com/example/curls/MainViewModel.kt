package com.example.curls

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curls.entity.MuscleGroup
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository,
): ViewModel() {
    val groups: MutableState<List<MuscleGroup>> = mutableStateOf(emptyList())

    init {
        loadData(true)
    }

    fun loadData(forceReload: Boolean) = viewModelScope.launch {
        groups.value = repository.getMuscleGroups(forceReload)
    }
}