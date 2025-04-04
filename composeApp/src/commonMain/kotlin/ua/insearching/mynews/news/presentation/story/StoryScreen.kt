package ua.insearching.mynews.news.presentation.story

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import ua.insearching.mynews.news.domain.model.Story
import ua.insearching.mynews.news.presentation.components.HtmlContent

@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController,
    story: Story
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets.safeDrawing,
        contentColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        if (story.content == null) {
            val webViewState = rememberWebViewState(story.guid)
            val loadingState = webViewState.loadingState
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            WebView(
                webViewState,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            HtmlContent(
                story.content,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}