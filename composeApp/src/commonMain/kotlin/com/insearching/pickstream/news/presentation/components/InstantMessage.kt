package com.insearching.pickstream.news.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun InstantMessage(
    modifier: Modifier = Modifier,
    duration: Duration = 3.seconds,
    message: String
) {
    var isShown by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isShown = true
        delay(duration)
        isShown = false
    }
    AnimatedVisibility(
        visible = isShown
    ) {
        Text(
            modifier = modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Red),
            text = message,
            color = MaterialTheme.colors.onPrimary,
            style = TextStyle(fontSize = 24.sp)
        )
    }
}