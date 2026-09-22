package com.example.curls.features.exercises.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.features.exercises.datasource.domain.Equipment
import com.example.curls.features.exercises.datasource.domain.Exercise
import com.example.curls.features.exercises.datasource.domain.Image
import com.example.curls.features.exercises.datasource.domain.Muscle
import com.example.curls.features.exercises.datasource.domain.Translation
import com.example.curls.features.exercises.datasource.domain.Video
import com.example.curls.ui.texts.TextHeadlineMedium
import com.example.curls.ui.theme.AppTheme
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText

@Composable
fun ExercisesScreen(state: ExercisesScreenState) {
    val listState = rememberLazyListState()
    LaunchedEffect(state.exercises.value) {
            listState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .statusBarsPadding()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedVisibility(visible = state.categories.value.isNotEmpty()) {
            FilterChips(
                categories = state.categories.value,
                selectedCategories = state.selectedCategories.value,
                onSelect = { state.onClick(it) }
            )
        }

        LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items = state.exercises.value, key = { it.id }) {
                ExerciseCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    exercise = it,
                )
            }
        }
    }
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
private fun ExerciseCard(modifier: Modifier = Modifier, exercise: Exercise) {
    val isExpanded = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .clip(CardDefaults.shape)
            .clickable { isExpanded.value = !isExpanded.value },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            TextHeadlineMedium(text = exercise.translation.name)

            exercise.translation.description?.let { Description(it, isExpanded.value) }
            exercise.images.forEach {
                AsyncImage(
                    modifier = Modifier.size(64.dp),
                    model = it.thumbnailMedium,
                    contentDescription = exercise.translation.description,
                )
            }
        }
    }
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
private fun Description(description: String, isExpanded: Boolean) {
    val state = rememberRichTextState()
    LaunchedEffect(description) { state.setHtml(description) }
    RichText(
        modifier = Modifier
            .animateContentSize()
            .then(if (isExpanded) Modifier else Modifier.height(0.dp))
            .clipToBounds(),
        state = state,
    )
}

@Composable
private fun FilterChips(
    categories: List<Category>,
    selectedCategories: List<Category>,
    onSelect: (Category) -> Unit,
) {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        categories.forEach { currentCategory ->
            val isCategorySelected = currentCategory in selectedCategories

            Text(
                text = currentCategory.name,
                style = MaterialTheme.typography.displaySmall,
                color = if (isCategorySelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .background(
                        color = if (isCategorySelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSelect(currentCategory) }
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExercisesScreenPreview() {
    AppTheme {
        ExercisesScreen(
            state = ExercisesScreenState(
                categories = mutableStateOf(
                    listOf(
                        Category(id = 0, name = "zero"),
                        Category(id = 1, name = "one"),
                        Category(id = 2, name = "two"),
                        Category(id = 3, name = "three"),
                        Category(id = 4, name = "four")
                    )
                ),
                exercises = mutableStateOf(
                    value =
                        listOf(
                            Exercise(
                                id = 0,
                                category = Category(id = 10, name = "abs"),
                                translation = Translation(
                                    name = "Crunches",
                                    description = "some description for crunches",
                                ),
                                images = listOf(
                                    Image(
                                        url = "https://wger.de/media/exercise-images/12/4a42cc6f-648d-40cc-a72a-c49dd47e1667.webp",
                                        thumbnailSmall = "https://wger.de/media/exercise-images/12/4a42cc6f-648d-40cc-a72a-c49dd47e1667.webp.200x200_q85.png",
                                        thumbnailMedium = "https://wger.de/media/exercise-images/12/4a42cc6f-648d-40cc-a72a-c49dd47e1667.webp.400x400_q85.png",
                                    ),
                                ),
                                videos = listOf(
                                    Video(
                                        videoUrl = "https://wger.de/media/exercise-video/12/5148c579-5df2-4618-9a7b-a2e29ac4dd7d.MOV",
                                        duration = "20.86",
                                        width = 1920L,
                                        height = 1080L,
                                    ),
                                ),
                                muscles = listOf(
                                    Muscle(
                                        name = "Biceps femoris",
                                        nameEn = "Hamstrings",
                                        imageUrlMain = "https://wger.de/static/images/muscles/main/muscle-11.54ef31755917.svg",
                                        imageUrlSecondary = "https://wger.de/static/images/muscles/secondary/muscle-11.a2bc76fe157a.svg",
                                    ),
                                    Muscle(
                                        name = "Gluteus maximus",
                                        nameEn = "Glutes",
                                        imageUrlMain = "https://wger.de/static/images/muscles/main/muscle-8.fbdfb46f3bc0.svg",
                                        imageUrlSecondary = "https://wger.de/static/images/muscles/secondary/muscle-8.605e4e1a0277.svg",
                                    ),
                                ),
                                secondaryMuscles = listOf(
                                    Muscle(
                                        name = "Quadriceps femoris",
                                        nameEn = "Quads",
                                        imageUrlMain = "https://wger.de/static/images/muscles/main/muscle-10.b1445ea1acf6.svg",
                                        imageUrlSecondary = "https://wger.de/static/images/muscles/secondary/muscle-10.55e36d852778.svg",
                                    ),
                                    Muscle(
                                        name = "Rectus abdominis",
                                        nameEn = "Abs",
                                        imageUrlMain = "https://wger.de/static/images/muscles/main/muscle-6.592f938fa8c7.svg",
                                        imageUrlSecondary = "https://wger.de/static/images/muscles/secondary/muscle-6.370f77c2860e.svg",
                                    ),
                                ),
                                equipment = listOf(
                                    Equipment("Kettlebell"),
                                )
                            )
                        )
                )
            )
        )
    }
}