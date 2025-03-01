package ua.insearching.mynews.news.presentation.add_feed

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mynews.composeapp.generated.resources.Res
import mynews.composeapp.generated.resources.add_feed
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ua.insearching.mynews.FAB_EXPLODE_BOUNDS_KEY
import ua.insearching.mynews.core.presentation.SwipeToDeleteContainer
import ua.insearching.mynews.news.domain.model.Channel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AddFeedScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: AddFeedScreenViewModel = koinViewModel<AddFeedScreenViewModel>(),
    backgroundColor: Color,
    scope: AnimatedVisibilityScope
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddFeedScreen(
        modifier = modifier,
        state = state,
        action = viewModel::onAction,
        backgroundColor = backgroundColor,
        scope = scope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AddFeedScreen(
    modifier: Modifier = Modifier,
    state: AddFeedScreenState,
    action: (AddFeedAction) -> Unit,
    backgroundColor: Color,
    scope: AnimatedVisibilityScope
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        backgroundColor = backgroundColor
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = FAB_EXPLODE_BOUNDS_KEY
                    ),
                    animatedVisibilityScope = scope
                ),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is AddFeedScreenState.Loading -> {
                    CircularProgressIndicator()
                }

                is AddFeedScreenState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = state.message.asString(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                showDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                        ) {
                            Text(stringResource(Res.string.add_feed))
                        }
                    }
                }

                is AddFeedScreenState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val channels = state.channels
                            items(channels, key = { it.link }) { channel ->
                                SwipeToDeleteContainer(
                                    item = channel,
                                    onDelete = {
                                        action(AddFeedAction.OnFeedRemove(channel))
                                    }
                                ) { swipeChannel: Channel ->
                                    ChannelItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        channel = swipeChannel,
                                        onClick = {
                                            action(AddFeedAction.OnFeedSelected(channel))
                                        }
                                    )
                                }
                            }
                        }
                        Button(
                            onClick = {
                                showDialog = true
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .padding(
                                    horizontal = 48.dp,
                                    vertical = 8.dp
                                ),
                        ) {
                            Text(stringResource(Res.string.add_feed))
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
                        action(AddFeedAction.OnFeedAdd(it))
                        showDialog = false
                    }
                )
            }
        }
    }
}