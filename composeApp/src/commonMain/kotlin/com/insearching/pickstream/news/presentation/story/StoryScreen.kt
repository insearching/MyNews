package com.insearching.pickstream.news.presentation.story

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.pickstream.news.data.BrowserLauncher
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.presentation.components.HtmlContent
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.ic_favorite
import pickstream.composeapp.generated.resources.ic_favorite_border

@Composable
fun StoryScreenRoot(
    modifier: Modifier = Modifier,
    browserLauncher: BrowserLauncher = koinInject(),
    viewModel: StoryScreenViewModel = koinViewModel(),
    story: Story
) {

    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize(story)
    }

    StoryScreen(
        modifier = modifier,
        story = story,
        isFavorite = isFavorite,
        onLinkClick = { url ->
            browserLauncher.openUrl(url)
        },
        onFavoriteClick = {
            viewModel.onAction(StoryViewAction.MarkUnmarkFavorite)
        }
    )
}

@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    story: Story,
    isFavorite: Boolean,
    onLinkClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(
            isFavorite = isFavorite,
            onFavoriteClick = onFavoriteClick
        )
        if (story.content == null) {
            val webViewState = rememberWebViewState(story.guid)
            val loadingState = webViewState.loadingState
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            WebView(state = webViewState)
        } else {
            HtmlContent(
                html = story.content,
                onLinkClick = onLinkClick
            )
        }
    }
}

@Composable
fun TopBar(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colors.onSurface)
    ) {
        Icon(
            painter = painterResource(if (isFavorite) Res.drawable.ic_favorite else Res.drawable.ic_favorite_border),
            contentDescription = if (isFavorite) "favorite" else "not favorite",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.CenterEnd)
                .clickable(onClick = onFavoriteClick)
        )
    }
}