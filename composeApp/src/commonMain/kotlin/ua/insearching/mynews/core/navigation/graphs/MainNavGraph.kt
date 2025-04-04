package ua.insearching.mynews.core.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import ua.insearching.mynews.core.navigation.Graph
import ua.insearching.mynews.core.navigation.Routes
import ua.insearching.mynews.news.presentation.favorites.FavoritesScreenRoot
import ua.insearching.mynews.news.presentation.home.HomeScreenRoot
import ua.insearching.mynews.news.presentation.notifications.NotificationsScreenRoot
import ua.insearching.mynews.news.presentation.profile.ProfileScreenRoot


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
                rootNavController = rootNavController,
                paddingValues = innerPadding,
                onStoryClick = { story ->
                    rootNavController.currentBackStackEntry?.savedStateHandle?.set("story", Json.encodeToString(story))
                    rootNavController.navigate(Routes.NewsDetails.route)
                }
            )
        }
        composable(route = Routes.Notifications.route) {
            NotificationsScreenRoot(
                rootNavController = rootNavController,
                paddingValues = innerPadding
            )
        }
        composable(route = Routes.Favorites.route) {
            FavoritesScreenRoot(
                rootNavController = rootNavController,
                paddingValues = innerPadding
            )
        }
        composable(route = Routes.Profile.route) {
            ProfileScreenRoot(
                rootNavController = rootNavController,
                paddingValues = innerPadding
            )
        }
    }
}