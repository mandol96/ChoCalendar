package com.cho.myapplication.config

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DayConfig(
    val size: Dp,
) {
    companion object {

        fun default() = DayConfig(
            size = 56.dp
        )
    }
}