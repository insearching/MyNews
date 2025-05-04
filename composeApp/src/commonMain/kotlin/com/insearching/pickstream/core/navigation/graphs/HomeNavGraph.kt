package com.insearching.pickstream.core.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.insearching.pickstream.core.navigation.Graph
import com.insearching.pickstream.core.navigation.Routes
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.presentation.story.StoryScreenRoot
import kotlinx.serialization.json.Json

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
                    StoryScreenRoot(
                        modifier = Modifier.padding(innerPadding),
                        story = story
                    )
                }
        }
    }
}