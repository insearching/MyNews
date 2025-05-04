package com.insearching.pickstream.news.domain.model

import com.prof18.rssparser.model.RssChannel
import com.insearching.pickstream.news.data.database.entity.ChannelEntity

data class Channel(
    val title: String,
    val link: String,
    val description: String,
    val imageUrl: String?,
    val stories: List<Story>,
    val isNotified: Boolean = false
)

fun RssChannel.toChannel(): Channel {
    return Channel(
        title = checkNotNull(title),
        link = checkNotNull(link),
        description = checkNotNull(description),
        imageUrl = image?.url,
        stories = items.map { it.toStory() }
    )
}

fun ChannelEntity.toChannel(): Channel {
    return Channel(
        title = title,
        link = link,
        description = description,
        imageUrl = imageUrl,
        stories = stories.map { it.toStory() },
        isNotified = isNotified
    )
}