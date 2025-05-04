package com.insearching.pickstream

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import com.insearching.pickstream.ui.theme.PickStreamTheme
import com.insearching.pickstream.news.presentation.main.MainScreen

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    PickStreamTheme {
        MainScreen()
    }
}