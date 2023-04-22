package com.github.chanjungkim.compose

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

sealed class ScrollbarState {
    object Idle : ScrollbarState()
    object ScrollingUp : ScrollbarState()
    object ScrollingDown : ScrollbarState()
}

@Composable
fun LazyScrollColumn(
    scrollbarSource: ScrollbarSource = ScrollbarSource(),
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var scrollbarOffsetY by remember { mutableStateOf(0) }
    var layoutHeight by remember { mutableStateOf(0) }
    var scrollbarState by remember { mutableStateOf<ScrollbarState>(ScrollbarState.Idle) }
    var prevScrollbarOffsetY by rememberSaveable { mutableStateOf(0) }
    var prevScrollOffset by rememberSaveable { mutableStateOf(0) }
    var prevItemIndex by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(state.firstVisibleItemScrollOffset) {

        scrollbarState = when {
            prevItemIndex < state.firstVisibleItemIndex || prevScrollOffset < state.firstVisibleItemScrollOffset -> ScrollbarState.ScrollingUp
            prevItemIndex > state.firstVisibleItemIndex || prevScrollOffset > state.firstVisibleItemScrollOffset -> ScrollbarState.ScrollingDown
            else -> ScrollbarState.Idle
        }
        Log.d("aos", "${scrollbarState}: scrollbarOffsetY: $scrollbarOffsetY, lazyListState.firstVisibleItemIndex: ${state.firstVisibleItemIndex}, lazyListState.firstVisibleItemScrollOffset: ${state.firstVisibleItemScrollOffset}")

        val offsetDiff = prevScrollOffset - state.firstVisibleItemScrollOffset

        when (scrollbarState) {
            ScrollbarState.ScrollingUp -> {
                if(offsetDiff > 0) {
                    scrollbarOffsetY = prevScrollOffset + state.firstVisibleItemScrollOffset
                } else {
                    scrollbarOffsetY = prevScrollOffset
                }
            }
            ScrollbarState.ScrollingDown -> {

            }
            ScrollbarState.Idle -> {
                // no-op
            }
        }
        prevScrollOffset = state.firstVisibleItemScrollOffset
        prevItemIndex = state.firstVisibleItemIndex
    }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                layoutHeight = it.size.height
            }
    ) {
        LazyColumn(
            state = state,
            modifier = Modifier
                .height(with(LocalDensity.current) {
                    layoutHeight.toDp()
                }),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
        ) {
            content()
        }

        AnimatedVisibility(
            visible = state.isScrollInProgress,
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
                                state.animateScrollBy(delta * 1000)
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