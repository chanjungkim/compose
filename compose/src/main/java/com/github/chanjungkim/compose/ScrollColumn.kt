package com.github.chanjungkim.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.delay
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
    var scrollbarOffsetY by remember { mutableStateOf(scrollState.value) }
    var layoutHeight by remember { mutableStateOf(0) }

    LaunchedEffect(scrollState.value) {
        val percentage = scrollState.value.toFloat() / scrollState.maxValue.toFloat()
        val position = percentage * layoutHeight
        scrollbarOffsetY = if (layoutHeight - position <= scrollbarSource.height.value) {
            position.toInt() - scrollbarSource.height.value.toInt()
        } else {
            position.toInt()
        }
    }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                layoutHeight = it.size.height
            }
    ) {
        Column(
            modifier = modifier
                .verticalScroll(scrollState),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
        ) {
            content()
        }

        AnimatedVisibility(
            visible = scrollState.isScrollInProgress,
            enter = fadeIn(),
            exit = fadeOut(
                animationSpec = keyframes {
                    this.durationMillis = scrollbarSource.fadeDuration
                }),
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scrollbarSource.thickness)
                    .background(scrollbarSource.trackColor)
            )
            Box(
                modifier = Modifier
                    .height(scrollbarSource.height)
                    .width(scrollbarSource.thickness)
                    .offset(y = with(LocalDensity.current) {
                        scrollbarOffsetY.toDp()
                    })
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                scrollState.animateScrollBy(delta * 1000)
                            }
                        }
                    )
                    .background(
                        scrollbarSource.thumbColor,
                        RoundedCornerShape(scrollbarSource.radius)
                    )
            )
        }
    }
}