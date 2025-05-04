package com.insearching.pickstream.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person

object Graph {
    const val NAVIGATION_BAR_SCREEN_GRAPH = "navigationBarScreenGraph"
}

sealed class Routes(var route: String) {
    data object Home : Routes("home")
    data object Favorites : Routes("favorites")
    data object Notifications : Routes("notifications")
    data object Profile : Routes("profile")

    data object NewsDetails: Routes("newsDetails")

}

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        title = "Home",
        route = Routes.Home.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
        title = "Favorites",
        route = Routes.Favorites.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Notifications,
        selectedIcon = Icons.Filled.Notifications,
        title = "Notifications",
        route = Routes.Notifications.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        title = "Settings",
        route = Routes.Profile.route,
    ),
)