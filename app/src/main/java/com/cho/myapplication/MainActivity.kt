package com.cho.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cho.myapplication.component.Day
import com.cho.myapplication.component.Header
import com.cho.myapplication.config.DayConfig
import com.cho.myapplication.config.DayLabelConfig
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MainActivity : ComponentActivity() {

    private val now = Clock.System.now()
    private val currentDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    private val currentDate: LocalDate = currentDateTime.date
    private val initialMonth = currentDate.month
    private val initialYear = currentDate.year
    private val startDayOfWeek = DayOfWeek.SUNDAY
    private val dayOfWeek = DayOfWeek.entries.rotate(startDayOfWeek.ordinal)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var month by remember { mutableStateOf(initialMonth) }
            var year by remember { mutableStateOf(initialYear) }
            val currentMonth = remember(month, year) {
                LocalDate(year, month, 1)
            }
            val displayDates by remember(currentMonth, startDayOfWeek) {
                mutableStateOf(getMonthDates(currentMonth, startDayOfWeek))
            }

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.Blue)
            )
            {
                Column(
                    modifier = Modifier
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Header(
                        month = month,
                        year = year,
                        modifier = Modifier.wrapContentSize(),
                        onPreviousClick = {
                            if (month == Month.JANUARY) {
                                month = Month.DECEMBER
                                year -= 1
                            } else {
                                month = Month.entries[month.ordinal - 1]
                            }
                        },
                        onNextClick = {
                            if (month == Month.DECEMBER) {
                                month = Month.JANUARY
                                year += 1
                            } else {
                                month = Month.entries[month.ordinal + 1]
                            }
                        }
                    )
                    Scaffold(
                        modifier = Modifier
                            .fillMaxWidth(),
                        dayOfWeek = { dayOfWeek },
                        dayLabelConfig = DayLabelConfig(true, 1),
                        dates = { displayDates }
                    ) { date ->
                        if (date.month == currentMonth.month) {
                            Day(
                                date = date,
                                dayConfig = if (date.dayOfWeek == DayOfWeek.SUNDAY) {
                                    DayConfig.default().copy(textColor = Color.Red)
                                } else {
                                    DayConfig.default()
                                }
                            )
                        } else {
                            Day(
                                date = date,
                                dayConfig = DayConfig.default().copy(textColor = Color.LightGray)
                            )
                        }
                    }
                }
            }
        }
    }
}


fun List<DayOfWeek>.rotate(distance: Int): List<DayOfWeek> {
    return this.drop(distance) + this.take(distance)
}


fun getMonthDates(
    currentMonth: LocalDate,
    startDayOfWeek: DayOfWeek
): List<LocalDate> {
    val firstDayOfMonth = LocalDate(currentMonth.year, currentMonth.month, 1)

    val firstDayOffset = (firstDayOfMonth.dayOfWeek.ordinal - startDayOfWeek.ordinal + 7) % 7
    val calendarStartDate = firstDayOfMonth.minus(firstDayOffset, DateTimeUnit.DAY)

    return (0 until 42).map { i ->
        calendarStartDate.plus(i.toLong(), DateTimeUnit.DAY)
    }
}