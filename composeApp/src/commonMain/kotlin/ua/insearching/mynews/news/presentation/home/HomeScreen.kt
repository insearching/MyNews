package ua.insearching.mynews.news.presentation.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import fadingEdge
import org.koin.compose.viewmodel.koinViewModel
import ua.insearching.mynews.news.domain.model.Story

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreenRoot(
    rootNavController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HomeViewAction.OnStoryClick -> onStoryClick(action.story)
                else -> Unit
            }
            viewModel.onAction(action)
        },
        modifier = modifier.padding(paddingValues)
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    state: HomeViewState,
    onAction: (HomeViewAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is HomeViewState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }

            is HomeViewState.Success -> {
                FeedContent(
                    stories = state.stories,
                    onAction = onAction,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is HomeViewState.Error -> {
                Text(
                    text = state.message.asString(),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FeedContent(
    stories: List<Story>,
    onAction: (HomeViewAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(stories) {
            StoryItem(
                story = it,
                onClick = {
                    onAction(HomeViewAction.OnStoryClick(it))
                }
            )
        }
    }
}