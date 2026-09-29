package com.example.curls.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlowList(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    itemVerticalAlignment: Alignment.Vertical = Alignment.Top,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    content: @Composable (FlowRowScope.() -> Unit)
) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
        itemVerticalAlignment = itemVerticalAlignment,
        maxItemsInEachRow = maxItemsInEachRow,
        maxLines = maxLines,
        content = content,
    )
}