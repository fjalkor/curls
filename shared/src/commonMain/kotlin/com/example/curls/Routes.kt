package com.example.curls

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.curls.features.exercisedetails.ExerciseDetailsScreen
import com.example.curls.features.exercisedetails.ExerciseDetailsViewModel
import com.example.curls.features.exercises.ui.ChooseExercisePager
import com.example.curls.features.exercises.ui.ExercisesViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data class Details(val exerciseId: Long) : Screen

}

@Composable
fun Routes() {
    val navHostController = rememberNavController()

    NavHost(navHostController, startDestination = Screen.Home) {
        composable<Screen.Home>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            val viewModel = koinViewModel<ExercisesViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()

            ChooseExercisePager(
                state = state.value,
                onEquipmentTapped = { viewModel.onEquipmentTapped(it) },
                onCategoryTapped = { viewModel.onCategoryTapped(it) },
                onPageChanged = { viewModel.updatePage(it) },
                onSelectExercise = { navHostController.navigate(Screen.Details(it.id)) },
            )
        }
        composable<Screen.Details>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            val selectedExerciseId = it.toRoute<Screen.Details>().exerciseId
            val viewModel = koinViewModel<ExerciseDetailsViewModel>(parameters = {
                parametersOf(selectedExerciseId)
            })
            val exercise = viewModel.exercise.collectAsStateWithLifecycle()

            exercise.value?.let { ExerciseDetailsScreen(exercise = it, onVideoClick = {}) }
                ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
        }
    }
}