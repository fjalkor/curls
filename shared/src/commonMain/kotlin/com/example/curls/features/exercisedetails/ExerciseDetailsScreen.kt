package com.example.curls.features.exercisedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.curls.debug.PreviewHelper
import com.example.curls.features.exercises.datasource.domain.Exercise
import com.example.curls.features.exercises.datasource.domain.Video
import com.example.curls.ui.FlowList
import com.example.curls.ui.SelectionChip
import com.example.curls.ui.theme.AppTheme
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText

@Composable
fun ExerciseDetailsScreen(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    onVideoClick: (Video) -> Unit,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ImageGallery(exercise)

        Spacer(Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            TitleAndCategory(exercise)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Description(exercise)
                Muscles(exercise)
                Equipment(exercise)
                VideoGallery(exercise, onVideoClick)
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun ImageGallery(exercise: Exercise) {
    val pager = rememberPagerState { exercise.images.size }

    if (exercise.images.isNotEmpty()) {
        Box {
            HorizontalPager(pager, Modifier.fillMaxWidth().height(260.dp)) { page ->
                AsyncImage(
                    model = exercise.images[page].url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            Text(
                "${pager.currentPage + 1}/${exercise.images.size}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp),
            )
        }
    }
}

@Composable
private fun TitleAndCategory(exercise: Exercise) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SelectionChip(text = exercise.category.name)
        Text(
            exercise.translation.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
private fun Description(exercise: Exercise) {
    exercise.translation.description?.takeIf { it.isNotEmpty() }?.let {
        val state = rememberRichTextState()
        Column {
            LaunchedEffect(it) { state.setHtml(it) }
            RichText(
                state = state,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun Muscles(exercise: Exercise) {
    val muscles = exercise.muscles + exercise.secondaryMuscles

    if (muscles.isNotEmpty()) {
        Column {
            SectionTitle("Muscles")

            FlowList {
                muscles.forEach {
                    SelectionChip(
                        text = it.name,
                        isSelected = it in exercise.muscles,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun Equipment(exercise: Exercise) {
    if (exercise.equipment.isNotEmpty()) {
        Column {
            SectionTitle("Equipment")

            FlowList { exercise.equipment.forEach { SelectionChip(text = it.name) } }
        }
    }
}

@Composable
private fun VideoGallery(exercise: Exercise, onVideoClick: (Video) -> Unit) {
    if (exercise.videos.isNotEmpty()) {
        Column {
            SectionTitle("Videos")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(exercise.videos) { video ->
                    Card(onClick = { onVideoClick(video) }, modifier = Modifier.size(200.dp, 120.dp)) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.PlayCircle, null, Modifier.size(48.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun ExerciseDetailsScreenPreview() {
    AppTheme {
        ExerciseDetailsScreen(
            exercise = PreviewHelper.debugExercise,
            onVideoClick = {},
        )
    }
}