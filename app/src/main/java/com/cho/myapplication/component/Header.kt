package com.cho.myapplication.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Month
import kotlinx.datetime.number

@Composable
fun Header(
    month: Month,
    year: Int,
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
) {

    val titleText =
        remember(year, month) { getTitleText(year, month) }

    HeaderContent(
        modifier = modifier.defaultMinSize(minHeight = 56.dp),
        titleText = titleText,
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
    )
}

@Composable
private fun HeaderContent(
    titleText: String,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isNext by rememberSaveable { mutableStateOf(true) }
    val paddingModifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

    Row(
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight()
            .then(paddingModifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomIconButton(
            modifier = Modifier.wrapContentSize(),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Previous Month",
            enabled = true,
            onClick = {
                isNext = false
                onPreviousClick()
            }
        )

        AnimatedContent(
            targetState = titleText,
            transitionSpec = {
                slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
            }
        ) { month ->
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                text = month,
            )
        }

        CustomIconButton(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            modifier = Modifier.wrapContentSize(),
            contentDescription = "Next Month",
            onClick = {
                isNext = true
                onNextClick()
            },
            enabled = true
        )
    }
}

fun getTitleText(year: Int, month: Month): String {
    val monthValue = month.number

    val shortYear = year.toString().takeLast(2)
    return "${shortYear}년${monthValue}월"
}