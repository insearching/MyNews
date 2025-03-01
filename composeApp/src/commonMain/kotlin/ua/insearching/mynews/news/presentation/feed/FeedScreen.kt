package ua.insearching.mynews.news.presentation.feed

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ua.insearching.mynews.FAB_EXPLODE_BOUNDS_KEY
import ua.insearching.mynews.news.domain.model.Story

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FeedScreenRoot(
    viewModel: FeedViewModel = koinViewModel(),
    onStoryClick: (Story) -> Unit,
    fabColor: Color,
    scope: AnimatedVisibilityScope,
    onFeedAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FeedScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is FeedAction.OnStoryClick -> onStoryClick(action.story)
                else -> Unit
            }
            viewModel.onAction(action)
        },
        fabColor = fabColor,
        scope = scope,
        onFeedAddClick = onFeedAddClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FeedScreen(
    state: FeedState,
    fabColor: Color,
    scope: AnimatedVisibilityScope,
    onFeedAddClick: () -> Unit,
    onAction: (FeedAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = fabColor,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp
                ),
                onClick = onFeedAddClick,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = FAB_EXPLODE_BOUNDS_KEY
                        ),
                        animatedVisibilityScope = scope
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is FeedState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }

                is FeedState.Success -> {
                    FeedContent(
                        stories = state.stories,
                        onAction = onAction,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is FeedState.Error -> {
                    Text(
                        text = state.message.asString(),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FeedContent(
    stories: List<Story>,
    onAction: (FeedAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(stories) {
            StoryItem(
                story = it,
                onClick = {
                    onAction(FeedAction.OnStoryClick(it))
                }
            )
        }
    }
}