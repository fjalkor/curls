package com.example.curls

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.curls.features.exercises.ExercisesScreen
import com.example.curls.features.exercises.ExercisesViewModel
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
            ExercisesScreen(viewModel.state)
        }
    }
}