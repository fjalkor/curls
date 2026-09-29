package com.example.curls.features.exercises.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.curls.debug.PreviewHelper
import com.example.curls.features.exercises.datasource.domain.Exercise
import com.example.curls.ui.texts.TextHeadlineMedium
import com.example.curls.ui.theme.AppTheme
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun ExerciseCard(modifier: Modifier = Modifier, exercise: Exercise, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .clip(CardDefaults.shape)
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            TextHeadlineMedium(text = exercise.translation.name)

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

@Preview
@Composable
private fun ExerciseCardPreview() {
    AppTheme {
        ExerciseCard(
            exercise = PreviewHelper.debugExercise,
            onClick = {},
        )
    }
}