package ua.insearching.mynews.news.presentation.profile

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mynews.composeapp.generated.resources.Res
import mynews.composeapp.generated.resources.add_feed
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ua.insearching.mynews.core.presentation.component.SwipeToDeleteContainer
import ua.insearching.mynews.news.domain.model.Channel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = koinViewModel<ProfileScreenViewModel>(),
    rootNavController: NavHostController,
    paddingValues: PaddingValues,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        modifier = modifier.padding(paddingValues),
        state = state,
        action = viewModel::onAction,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    action: (ProfileAction) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is ProfileScreenState.Loading -> {
                CircularProgressIndicator()
            }

            is ProfileScreenState.Error -> {
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
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    AddFeedButton(
                        onShowDialog = {
                            showDialog = true
                        }
                    )
                }
            }

            is ProfileScreenState.Success -> {
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
                                    action(ProfileAction.OnFeedRemove(channel))
                                }
                            ) { swipeChannel: Channel ->
                                ChannelItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    channel = swipeChannel,
                                    onClick = {
                                        action(ProfileAction.OnFeedSelected(channel))
                                    }
                                )
                            }
                        }
                    }
                    AddFeedButton(onShowDialog = {
                        showDialog = true
                    })
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
fun AddFeedButton(
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
        modifier = Modifier
            .padding(
                horizontal = 48.dp,
                vertical = 8.dp
            ),
    ) {
        Text(stringResource(Res.string.add_feed))
    }
}