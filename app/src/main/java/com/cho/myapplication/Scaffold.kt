package com.cho.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cho.myapplication.config.DayLabelConfig
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
fun Scaffold(
    dayOfWeek: () -> List<DayOfWeek>,
    dayLabelConfig: DayLabelConfig,
    modifier: Modifier = Modifier,
    dates: () -> List<LocalDate>,
    content: @Composable (LocalDate) -> Unit,
) {
    val displayDates = dates()
    val displayDayOfWeek = dayOfWeek()

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.Center,
        content = {
            items(displayDayOfWeek) { dayOfWeek ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayOfWeek.toKoreanString(),
                        color = if (dayOfWeek == DayOfWeek.SUNDAY) Color.Red else Color.Black
                    )
                }
            }
            items(items = displayDates) { date ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    content(date)
                }
            }
        }
    )
}

fun DayOfWeek.toKoreanString(): String {
    return when (this) {
        DayOfWeek.SUNDAY -> "일"
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
    }
}