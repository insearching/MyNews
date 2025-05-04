package com.insearching.pickstream.news.presentation.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.presentation.components.FeedContent
import com.insearching.pickstream.news.presentation.profile.AddFeedDialog
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreenRoot(
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
        modifier = modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    state: HomeViewState,
    onAction: (HomeViewAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is HomeViewState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                )
            }

            is HomeViewState.Success -> {
                FeedContent(
                    stories = state.stories,
                    onStorySelected = { onAction(HomeViewAction.OnStoryClick(it)) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            is HomeViewState.Error -> {
                val shape = RoundedCornerShape(16.dp)
                Box(
                    modifier = Modifier
                        .clip(shape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, shape)
                        .clickable {
                            showDialog = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message.asString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddFeedDialog(
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                onAction(HomeViewAction.OnAddFeed(it))
                showDialog = false
            }
        )
    }
}