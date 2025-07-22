package com.cho.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = modifier,
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.Center,
        content = {
            items(displayDayOfWeek) { dayOfWeek ->
                Text(
                    text = dayOfWeek.name.take(dayLabelConfig.textCharCount),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            items(items = displayDates) { date ->
                content(date)
            }
        }
    )
}