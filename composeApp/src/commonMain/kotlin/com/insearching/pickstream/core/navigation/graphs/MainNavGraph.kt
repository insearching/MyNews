package com.insearching.pickstream.core.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.insearching.pickstream.core.navigation.Graph
import com.insearching.pickstream.core.navigation.Routes
import com.insearching.pickstream.news.presentation.favorites.FavoritesScreenRoot
import com.insearching.pickstream.news.presentation.home.HomeScreenRoot
import com.insearching.pickstream.news.presentation.notifications.NotificationsScreenRoot
import com.insearching.pickstream.news.presentation.profile.ProfileScreenRoot
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun NavGraphBuilder.mainNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues
) {
    navigation(
        startDestination = Routes.Home.route,
        route = Graph.NAVIGATION_BAR_SCREEN_GRAPH
    ) {
        composable(route = Routes.Home.route) {
            HomeScreenRoot(
                modifier = Modifier.padding(paddingValues = innerPadding),
                onStoryClick = { story ->
                    rootNavController.currentBackStackEntry?.savedStateHandle?.set("story", Json.encodeToString(story))
                    rootNavController.navigate(Routes.NewsDetails.route)
                }
            )
        }
        composable(route = Routes.Notifications.route) {
            NotificationsScreenRoot(
                modifier = Modifier.padding(paddingValues = innerPadding),
            )
        }
        composable(route = Routes.Favorites.route) {
            FavoritesScreenRoot(
                modifier = Modifier.padding(paddingValues = innerPadding),
                onStoryClick = { story ->
                    rootNavController.currentBackStackEntry?.savedStateHandle?.set("story", Json.encodeToString(story))
                    rootNavController.navigate(Routes.NewsDetails.route)
                }
            )
        }
        composable(route = Routes.Profile.route) {
            ProfileScreenRoot(
                modifier = Modifier.padding(paddingValues = innerPadding),
            )
        }
    }
}