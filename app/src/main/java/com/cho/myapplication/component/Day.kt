package com.cho.myapplication.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cho.myapplication.config.DayConfig
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@Composable
fun Day(
    date: LocalDate,
    modifier: Modifier = Modifier,
    dayConfig: DayConfig = DayConfig.default(),
) {
    DayContent(
        date = date,
        modifier,
        dayConfig
    )
}

@OptIn(ExperimentalTime::class)
@Composable
fun DayContent(
    date: LocalDate,
    modifier: Modifier = Modifier,
    dayConfig: DayConfig = DayConfig.default(),
) {
    val today = remember(TimeZone.currentSystemDefault()) {
        Clock.System.todayIn(TimeZone.currentSystemDefault())
    }
    val currentDay = today == date

    Column(
        modifier = modifier
            .border(
                border = getBorderStroke(
                    currentDay = currentDay,
                    color = Color.Black,
                ),
                shape = CircleShape
            )
    ) {
        Text(
            text = date.day.toString(),
            color = dayConfig.textColor,
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
        )
    }
}

private fun getBorderStroke(
    currentDay: Boolean,
    color: Color,
) = if (currentDay) {
    BorderStroke(1.dp, color)
} else {
    BorderStroke(0.dp, Color.Transparent)
}
