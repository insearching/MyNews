package ua.insearching.mynews.news.presentation.profile

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import mynews.composeapp.generated.resources.Res
import mynews.composeapp.generated.resources.news_error
import org.jetbrains.compose.resources.painterResource
import ua.insearching.mynews.core.presentation.PulseAnimation
import ua.insearching.mynews.news.domain.model.Channel

@Composable
fun ChannelItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    channel: Channel
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(24.dp)
        ) {
            Text(
                text = channel.title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.W400
                )
            )

            var imageLoadResult by remember {
                mutableStateOf<Result<Painter>?>(null)
            }
            val painter = rememberAsyncImagePainter(
                model = channel.imageUrl,
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
                    modifier = Modifier.size(60.dp)
                )

                else -> {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onTertiaryContainer)
                    ) {
                        Image(
                            painter = if (result.isSuccess) {
                                painter
                            } else {
                                painterResource(Res.drawable.news_error)
                            },
                            contentDescription = channel.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(16.dp)
                                .aspectRatio(
                                    ratio = 16f / 9f,
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
        }
    }
}