package com.cho.myapplication.config

data class DayLabelConfig(
    val centerAligned: Boolean,
    val textCharCount: Int
) {
    companion object {
        fun default() = DayLabelConfig(
            centerAligned = true,
            textCharCount = 1,
        )
    }
}