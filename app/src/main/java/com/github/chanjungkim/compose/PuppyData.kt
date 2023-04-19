package com.github.chanjungkim.compose

import androidx.annotation.DrawableRes

data class PuppyData (
    @DrawableRes val resId: Int,
    val name: String,
    val age: Int,
    val description: String
)
