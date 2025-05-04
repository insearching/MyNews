package com.insearching.pickstream

import androidx.compose.ui.window.ComposeUIViewController
import com.insearching.pickstream.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}