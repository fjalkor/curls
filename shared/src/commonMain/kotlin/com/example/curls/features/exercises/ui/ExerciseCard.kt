package com.example.curls.features.exercises.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.curls.features.exercises.datasource.domain.Exercise
import com.example.curls.features.exercises.datasource.domain.Muscle
import com.example.curls.features.exercises.datasource.domain.Translation
import com.example.curls.ui.texts.TextHeadlineMedium
import com.example.curls.ui.theme.AppTheme
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun ExerciseCard(modifier: Modifier = Modifier, exercise: Exercise) {
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

@Preview
@Composable
private fun ExerciseCardPreview() {
    AppTheme {
        ExerciseCard(
            exercise = Exercise(
                id = 0L,
                category = Category(
                    id = 0,
                    name = "abs",
                ),
                translation = Translation(
                    name = "Crunches",
                    description = "some more detailed description of this exercise",
                ),
                images = emptyList(),
                videos = emptyList(),
                muscles = listOf(
                    Muscle(
                        name = "Rectus abdominis",
                        nameEn = "Abs",
                        imageUrlMain = "https://wger.de/static/images/muscles/main/muscle-6.592f938fa8c7.svg",
                        imageUrlSecondary = "https://wger.de/static/images/muscles/secondary/muscle-6.370f77c2860e.svg",
                    )
                ),
                secondaryMuscles = emptyList(),
                equipment = emptyList(),
            )
        )
    }
}