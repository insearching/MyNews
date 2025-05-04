package com.insearching.pickstream.news.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.insearching.pickstream.news.domain.model.Channel
import com.insearching.pickstream.news.presentation.components.NewsAsyncImage
import org.jetbrains.compose.resources.painterResource
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.ic_bookmark
import pickstream.composeapp.generated.resources.ic_bookmark_checked

@Composable
fun ChannelItem(
    modifier: Modifier = Modifier,
    channel: Channel,
    onClick: () -> Unit = {},
    onNotifyClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(
                text = channel.title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W400
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(if (channel.isNotified) Res.drawable.ic_bookmark_checked else Res.drawable.ic_bookmark),
                    contentDescription = if (channel.isNotified) "marked" else "not marked",
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(onClick = onNotifyClick)
                        .align(Alignment.CenterVertically)
                )
                NewsAsyncImage(
                    url = channel.title,
                    contentDescription = "channel image",
                    modifier = Modifier.size(72.dp)
                )
            }
        }
    }
}