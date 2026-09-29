package com.example.curls.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SelectionChip(
    text: String,
    isSelected: Boolean = false,
    style: TextStyle = MaterialTheme.typography.displaySmall,
    cornerRadiusDp: Dp = 16.dp,
    onClick: (() -> Unit)? = null) {
    val shape = RoundedCornerShape(cornerRadiusDp)
    Text(
        text = text,
        style = style,
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = shape,
            )
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                shape = shape,
            )
            .clip(shape)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            )
    )
}