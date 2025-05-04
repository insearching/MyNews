package com.insearching.pickstream.news.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.presentation.components.FeedContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel(),
    onStoryClick: (Story) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is FavoritesViewAction.OnStoryClick -> onStoryClick(action.story)
                else -> Unit
            }
            viewModel.onAction(action)
        },
        modifier = modifier
    )
}

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    state: FavoritesViewState,
    onAction: (FavoritesViewAction) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is FavoritesViewState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            is FavoritesViewState.Success -> {
                FeedContent(
                    stories = state.stories,
                    onStorySelected = { onAction(FavoritesViewAction.OnStoryClick(it)) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            is FavoritesViewState.Error -> {
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