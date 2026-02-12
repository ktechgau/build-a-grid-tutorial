package com.example.buildagrid.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes
    val subjectId: Int,
    val count: Int,
    @DrawableRes
    val subjectImageId: Int,
)
