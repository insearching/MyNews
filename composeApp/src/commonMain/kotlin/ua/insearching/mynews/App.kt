package ua.insearching.mynews

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import ua.insearching.mynews.ui.theme.MyNewsTheme
import ua.insearching.mynews.news.presentation.main.MainScreen

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    MyNewsTheme {
        MainScreen()
    }
}