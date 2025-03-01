package ua.insearching.mynews.news.domain.model

import com.prof18.rssparser.model.RssChannel
import ua.insearching.mynews.news.data.database.ChannelEntity

data class Channel(
    val title: String,
    val link: String,
    val description: String,
    val imageUrl: String?,
    val stories: List<Story>
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
        stories = stories.map { it.toStory() }
    )
}