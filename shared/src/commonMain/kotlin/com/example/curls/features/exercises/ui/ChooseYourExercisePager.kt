package com.example.curls.features.exercises.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.features.exercises.datasource.domain.Equipment
import com.example.curls.features.exercises.datasource.domain.Exercise
import com.example.curls.features.exercises.datasource.domain.Named
import com.example.curls.ui.theme.AppTheme
import curls.shared.generated.resources.Res
import curls.shared.generated.resources.button_label_confirm
import curls.shared.generated.resources.choose_exercise_first_page_title
import curls.shared.generated.resources.choose_exercise_second_page_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChooseExercisePager(
    state: ExercisesScreenUiState,
    onEquipmentSelected: (List<Equipment>) -> Unit,
    onCategoriesSelected: (List<Category>) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scope = rememberCoroutineScope()

    HorizontalPager(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
        userScrollEnabled = pagerState.currentPage != 0,
        state = pagerState,
    ) { pageNumber ->
        when (pageNumber) {
            0 -> MultipleSelectionPage(
                message = stringResource(Res.string.choose_exercise_first_page_title),
                availableOptions = state.availableEquipment,
                selection = state.selectedEquipment,
                onSelect = {
                    onEquipmentSelected(it)
                    scope.launch { pagerState.animateScrollToPage(1) }
                },
            )

            1 -> MultipleSelectionPage(
                message = stringResource(Res.string.choose_exercise_second_page_title),
                availableOptions = state.availableCategories,
                selection = state.selectedCategories,
                onSelect = {
                    onCategoriesSelected(it)
                    scope.launch { pagerState.animateScrollToPage(2) }
                }
            )

            2 -> ResultPage(exercises = state.exercises)
        }
    }
}

@Composable
fun ResultPage(exercises: List<Exercise>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = exercises, key = { it.id }) {
            ExerciseCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                exercise = it,
            )
        }
    }
}

@Composable
private fun <T : Named> MultipleSelectionPage(
    modifier: Modifier = Modifier,
    message: String,
    availableOptions: List<T>,
    selection: List<T>,
    onSelect: (List<T>) -> Unit = {},
) {
    val selection: MutableState<List<T>> = remember { mutableStateOf(selection) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = message,
            style = MaterialTheme.typography.displayLarge,
        )

        Spacer(Modifier.height(32.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            availableOptions.forEach { currentOption ->
                val chipShape = RoundedCornerShape(16.dp)
                val isSelected = currentOption in selection.value

                Text(
                    text = currentOption.name,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = chipShape,
                        )
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                            shape = chipShape,
                        )
                        .clip(chipShape)
                        .clickable {
                            if (isSelected) {
                                selection.value = selection.value.filter { it != currentOption }
                            } else {
                                selection.value += currentOption
                            }
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        )
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            enabled = selection.value.isNotEmpty(),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = ButtonDefaults.shape,
                ),
            onClick = { onSelect(selection.value) },
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.button_label_confirm),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}


private val debugEquipment = listOf(
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

private val debugCategories = listOf(
    Category(10L, "Abs"),
    Category(8L, "Arms"),
    Category(12L, "Back"),
    Category(14L, "Calves"),
    Category(15L, "Cardio"),
    Category(11L, "Chest"),
    Category(9L, "Legs"),
    Category(13L, "Shoulders"),
)

@Preview(showBackground = true)
@Composable
fun ChooseExercisePagerPreview() {
    AppTheme {
        ChooseExercisePager(
            state = ExercisesScreenUiState(
                availableEquipment = debugEquipment,
                availableCategories = debugCategories,
            ),
            onCategoriesSelected = {},
            onEquipmentSelected = {},
        )
    }
}