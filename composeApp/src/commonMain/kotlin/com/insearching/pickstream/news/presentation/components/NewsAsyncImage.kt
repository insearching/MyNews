package com.insearching.pickstream.news.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.insearching.pickstream.core.presentation.PulseAnimation
import org.jetbrains.compose.resources.painterResource
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.news_error

@Composable
fun NewsAsyncImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String,
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = url,
        onSuccess = {
            imageLoadResult =
                if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("Invalid image size"))
                }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )
    val painterState by painter.state.collectAsStateWithLifecycle()
    val transition by animateFloatAsState(
        targetValue = if (painterState is AsyncImagePainter.State.Success) 1f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    when (val result = imageLoadResult) {
        null -> PulseAnimation(
            modifier = modifier.fillMaxSize()
        )

        else -> {
            Image(
                painter = if (result.isSuccess) {
                    painter
                } else {
                    painterResource(Res.drawable.news_error)
                },
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .aspectRatio(
                        ratio = 1f / 1f,
                        matchHeightConstraintsFirst = true
                    )
                    .graphicsLayer {
                        rotationX = (1f - transition) * 30f
                        val scale = 0.8f + (0.2f * transition)
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}