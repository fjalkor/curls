package com.example.curls.features.exercises.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.curls.debug.PreviewHelper
import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.features.exercises.datasource.domain.Equipment
import com.example.curls.features.exercises.datasource.domain.Exercise
import com.example.curls.ui.theme.AppTheme
import curls.shared.generated.resources.Res
import curls.shared.generated.resources.choose_exercise_first_page_title
import curls.shared.generated.resources.choose_exercise_second_page_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChooseExercisePager(
    state: ExercisesScreenUiState,
    onEquipmentTapped: (Equipment) -> Unit,
    onCategoryTapped: (Category) -> Unit,
    onPageChanged: (Int) -> Unit,
    onSelectExercise: (Exercise) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = state.currentPage, pageCount = { 3 })

    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collectLatest {
            onPageChanged(it)
        }
    }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = pagerState.currentPage != 0,
        state = pagerState,
    ) { pageNumber ->
        when (pageNumber) {
            0 -> MultipleSelectionPage(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 32.dp),
                message = stringResource(Res.string.choose_exercise_first_page_title),
                availableOptions = state.availableEquipment,
                selection = state.selectedEquipment,
                onTap = { onEquipmentTapped(it) },
                nextPage = { onPageChanged(1) }
            )

            1 -> MultipleSelectionPage(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 32.dp),
                message = stringResource(Res.string.choose_exercise_second_page_title),
                availableOptions = state.availableCategories,
                selection = state.selectedCategories,
                onTap = { onCategoryTapped(it) },
                nextPage = { onPageChanged(2) }
            )

            2 -> ResultPage(
                modifier = Modifier.padding(horizontal = 8.dp),
                exercises = state.exercises,
                onSelectExercise = onSelectExercise,
            )
        }
    }
}

@Composable
fun ResultPage(
    modifier: Modifier = Modifier,
    exercises: List<Exercise>,
    onSelectExercise: (Exercise) -> Unit,
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = exercises, key = { it.id }) {
            ExerciseCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                exercise = it,
                onClick = { onSelectExercise(it) },
            )
        }
        item { Spacer(Modifier.height(32.dp)) }
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseExercisePagerPreview() {
    AppTheme {
        val state = remember {
            mutableStateOf(
                ExercisesScreenUiState(
                    availableEquipment = PreviewHelper.debugEquipment,
                    availableCategories = PreviewHelper.debugCategories,
                    exercises = listOf(PreviewHelper.debugExercise)
                )
            )
        }
        ChooseExercisePager(
            state = state.value,
            onPageChanged = { state.value = state.value.copy(currentPage = it) },
            onEquipmentTapped = { PreviewHelper.onItemTapped(it, state) },
            onCategoryTapped = { PreviewHelper.onItemTapped(it, state) },
            onSelectExercise = {},
        )
    }
}