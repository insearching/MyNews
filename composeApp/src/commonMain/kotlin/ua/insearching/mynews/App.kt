package ua.insearching.mynews

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import ua.insearching.MyNewsTheme
import ua.insearching.mynews.app.Route
import ua.insearching.mynews.news.domain.model.Story
import ua.insearching.mynews.news.presentation.add_feed.AddFeedScreenRoot
import ua.insearching.mynews.news.presentation.add_feed.AddFeedScreenViewModel
import ua.insearching.mynews.news.presentation.feed.FeedScreenRoot
import ua.insearching.mynews.news.presentation.feed.FeedViewModel
import ua.insearching.mynews.news.presentation.story.StoryScreen

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val stories = remember { mutableStateOf(mutableMapOf<String, Story>()) }

    MyNewsTheme {
        val backgroundColor = MaterialTheme.colorScheme.tertiary
        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = Route.FeedScreen,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                composable<Route.FeedScreen>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    FeedScreenRoot(
                        viewModel = koinViewModel<FeedViewModel>(),
                        onStoryClick = { story ->
                            stories.value[story.guid] = story
                            navController.navigate(Route.StoryScreen(story.guid))
                        },
                        onFeedAddClick = {
                            navController.navigate(Route.AddFeedScreen)
                        },
                        fabColor = backgroundColor,
                        scope = this
                    )
                }
                composable<Route.AddFeedScreen> {
                    AddFeedScreenRoot(
                        viewModel = koinViewModel<AddFeedScreenViewModel>(),
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        scope = this
                    )
                }
                composable<Route.StoryScreen>(
                    enterTransition = { slideInHorizontally { initialOffset -> initialOffset } },
                    exitTransition = { slideOutHorizontally { initialOffset -> initialOffset } }
                ) {
                    val guid = it.toRoute<Route.StoryScreen>().guid
                    val story = stories.value[guid] ?: return@composable
                    StoryScreen(
                        story = story
                    )
                }
            }
        }
    }
}