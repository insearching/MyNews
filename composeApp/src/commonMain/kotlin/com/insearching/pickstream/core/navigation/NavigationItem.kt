package com.insearching.pickstream.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val title: String,
    val route: String
)