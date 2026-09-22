package com.example.curls.ui.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.curls.ui.theme.AppTheme

@Composable
fun TextHeadlineMedium(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Preview(showBackground = true)
@Composable
private fun TextHeadlineMediumPreview() {
    AppTheme {
        TextHeadlineMedium(text = "I am headline medium")
    }
}