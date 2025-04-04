package ua.insearching.mynews.core.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.json.Json
import ua.insearching.mynews.core.navigation.Graph
import ua.insearching.mynews.core.navigation.Routes
import ua.insearching.mynews.news.domain.model.Story
import ua.insearching.mynews.news.presentation.story.StoryScreen

@Composable
fun RootNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = rootNavController,
        startDestination = Graph.NAVIGATION_BAR_SCREEN_GRAPH,
    ) {
        mainNavGraph(
            rootNavController = rootNavController,
            innerPadding = innerPadding
        )
        composable(
            route = Routes.NewsDetails.route,
        ) {
            rootNavController.previousBackStackEntry?.savedStateHandle?.get<String>("story")
                ?.let { jsonString ->
                    val story = Json.decodeFromString<Story>(jsonString)
                    StoryScreen(
                        rootNavController = rootNavController,
                        story = story
                    )
                }
        }
    }
}