package ua.insearching.mynews

import androidx.compose.ui.window.ComposeUIViewController
import ua.insearching.mynews.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}