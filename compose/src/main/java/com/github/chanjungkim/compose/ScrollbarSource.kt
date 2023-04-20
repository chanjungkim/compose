package com.github.chanjungkim.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScrollbarSource (
    val fadeDuration: Int = 500,
    val thumbColor: Color = Color(0xFF797979),
    val trackColor: Color = Color(0xFFE0E0E0),
    val thickness: Dp = 8.dp,
    val height: Dp = 60.dp,
    val radius: Dp = 2.dp,
    val scrollbarStyle: ScrollbarStyle = ScrollbarStyle.DEFAULT
) {
    enum class ScrollbarStyle {
        DEFAULT,
        OutsideInset,
        InsideInset
    }
}