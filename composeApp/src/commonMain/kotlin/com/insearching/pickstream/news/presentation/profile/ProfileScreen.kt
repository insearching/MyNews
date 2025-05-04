package com.insearching.pickstream.news.presentation.profile

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.pickstream.core.presentation.UiText
import com.insearching.pickstream.core.presentation.component.SwipeToDeleteContainer
import com.insearching.pickstream.news.domain.model.Channel
import com.insearching.pickstream.news.presentation.components.InstantMessage
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.add_feed

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = koinViewModel<ProfileScreenViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle(null)

    var showMessage by remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        if (message != null) {
            showMessage = true
            showMessage = false
        }
    }

    ProfileScreen(
        modifier = modifier,
        message = message,
        state = state,
        action = viewModel::onAction,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    message: UiText? = null,
    action: (ProfileAction) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        if (message != null) {
            InstantMessage(
                modifier = Modifier.align(Alignment.BottomCenter),
                message = message.asString()
            )
        }
        when (state) {
            is ProfileScreenState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }

            is ProfileScreenState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = state.message.asString(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                    AddFeedButton(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onShowDialog = {
                            showDialog = true
                        }
                    )
                }
            }

            is ProfileScreenState.Success -> {
                Box {
                    Channels(
                        modifier = Modifier.align(Alignment.Center),
                        state = state,
                        action = action
                    )
                    AddFeedButton(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onShowDialog = {
                            showDialog = true
                        }
                    )
                }
            }
        }
        if (showDialog) {
            AddFeedDialog(
                onDismiss = {
                    showDialog = false
                },
                onConfirm = {
                    action(ProfileAction.OnFeedAdd(it))
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun Channels(
    modifier: Modifier = Modifier,
    state: ProfileScreenState.Success,
    action: (ProfileAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
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
                        action(ProfileAction.OnFeedRemove(channel))
                    }
                ) { swipeChannel: Channel ->
                    ChannelItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        channel = swipeChannel,
                        onClick = {
                            action(ProfileAction.OnFeedSelected(channel))
                        },
                        onNotifyClick = {
                            action(ProfileAction.OnFeedNotified(channel))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddFeedButton(
    modifier: Modifier = Modifier,
    onShowDialog: () -> Unit
) {
    Button(
        onClick = {
            onShowDialog()
        },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .padding(
                horizontal = 48.dp,
                vertical = 8.dp
            ),
    ) {
        Text(stringResource(Res.string.add_feed))
    }
}