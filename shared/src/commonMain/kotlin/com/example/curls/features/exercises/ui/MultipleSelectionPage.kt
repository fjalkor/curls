package com.example.curls.features.exercises.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.curls.features.exercises.datasource.domain.Named
import com.example.curls.ui.FlowList
import com.example.curls.ui.SelectionChip
import com.example.curls.ui.theme.AppTheme
import curls.shared.generated.resources.Res
import curls.shared.generated.resources.button_label_confirm
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T : Named> MultipleSelectionPage(
    modifier: Modifier = Modifier.Companion,
    message: String,
    availableOptions: List<T>,
    selection: List<T>,
    onTap: (T) -> Unit = {},
    nextPage: () -> Unit = {},
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = message,
            style = MaterialTheme.typography.displayLarge,
        )

        Spacer(Modifier.height(32.dp))

        FlowList(modifier = Modifier.fillMaxWidth()) {
            availableOptions.forEach {
                SelectionChip(
                    text = it.name,
                    isSelected = it in selection,
                    onClick = { onTap(it) },
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            enabled = selection.isNotEmpty(),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = ButtonDefaults.shape,
                ),
            onClick = { nextPage() },
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

@Preview(showBackground = true)
@Composable
private fun MultipleSelectionPagePreview() {
    val options = listOf(
        object : Named { override val name: String = "one" },
        object : Named { override val name: String = "two" },
        object : Named { override val name: String = "three" },
        object : Named { override val name: String = "four" },
        object : Named { override val name: String = "five" },
        object : Named { override val name: String = "six" },
        object : Named { override val name: String = "seven" },
        object : Named { override val name: String = "eight" },
        object : Named { override val name: String = "nine" },
        object : Named { override val name: String = "ten" },
    )

    AppTheme {
        MultipleSelectionPage(
            message = "What options do you want to select?",
            availableOptions = options,
            selection = options.filter { it.name.length == 3 },
        )
    }
}