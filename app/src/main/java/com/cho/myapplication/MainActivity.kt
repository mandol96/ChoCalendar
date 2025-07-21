package com.cho.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cho.myapplication.component.Header
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var month by remember { mutableStateOf(initialMonth) }
            var year by remember { mutableStateOf(initialYear) }

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
        }
    }
}


