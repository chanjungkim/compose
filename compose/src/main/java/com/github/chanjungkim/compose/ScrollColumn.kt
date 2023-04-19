package com.github.chanjungkim.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScrollColumn(
    scrollbarSource: ScrollbarSource = ScrollbarSource(),
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val scrollbarOffsetY by remember { mutableStateOf(scrollState.value.dp) }

    Log.d("aos", "ScrollColumn: ${scrollState.value}")

    Box{
        Column(
            modifier = modifier
                .verticalScroll(scrollState),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
        ) {
            content()
        }

        Column(
            modifier = Modifier.fillMaxHeight()
        ){
            Text(text = "Hello", modifier = Modifier.absoluteOffset(y = scrollbarOffsetY))
        }
        Box(
            Modifier
                .height(scrollbarSource.scrollbarHeight)
                .width(scrollbarSource.scrollbarWidth)
                .align(Alignment.TopEnd)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.animateScrollBy(delta * 1000)
                        }
                    }
                )
                .drawBehind {
                    drawRect(color = scrollbarSource.scrollbarThumbColor)
                }
                .offset(y = scrollbarOffsetY)
        )
    }
}