package com.example.curls

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.curls.features.exercises.ui.ChooseExercisePager
import com.example.curls.features.exercises.ui.ExercisesViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

sealed interface Screen {
    @Serializable
    data object Home: Screen

}

@Composable
fun Routes() {
    val navHostController = rememberNavController()

    NavHost(navHostController, startDestination = Screen.Home) {
        composable<Screen.Home> {
            val viewModel = koinViewModel<ExercisesViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()

            ChooseExercisePager(
                state = state.value,
                onEquipmentTapped = { viewModel.onEquipmentTapped(it) },
                onCategoryTapped = { viewModel.onCategoryTapped(it) },
                onPageChanged = { viewModel.updatePage(it) }
            )
        }
    }
}