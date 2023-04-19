package com.github.chanjungkim.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScrollbarSource (
    val scrollbarFadeDuration: Int = 500,
    val scrollbarThumbColor: Color = Color(0xFF797979),
    val scrollbarTrackVertical: Color = Color(0xFFE0E0E0),
    val scrollbarWidth: Dp = 8.dp,
    val scrollbarHeight: Dp = 60.dp,
    val scrollbarStyle: ScrollbarStyle = ScrollbarStyle.DEFAULT
) {
    enum class ScrollbarStyle {
        DEFAULT,
        OutsideInset,
        InsideInset
    }
}