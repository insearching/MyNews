package ua.insearching.mynews.news.domain.model

import com.prof18.rssparser.model.RssItem
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ua.insearching.mynews.core.utils.DateUtils.parseISODateTime
import ua.insearching.mynews.core.utils.DateUtils.parseRFCDateTime
import ua.insearching.mynews.news.data.database.StoryEntity

@Serializable
data class Story(
    val guid: String,
    val title: String,
    val link: String,
    val channelName: String? = null,
    val categories: List<String>,
    val publicationDate: LocalDateTime?,
    val description: String?,
    val image: String?,
    val content: String?
)

fun RssItem.toStory(): Story {
    return Story(
        guid = checkNotNull(guid),
        title = checkNotNull(title),
        link = checkNotNull(link),
        categories = categories,
        publicationDate = pubDate?.parseRFCDateTime(),
        description = description,
        image = image,
        content = content
    )
}

fun StoryEntity.toStory(): Story {
    return Story(
        guid = guid,
        title = title,
        link = link,
        categories = categories,
        publicationDate = pubDate?.parseISODateTime(),
        description = description,
        image = image,
        content = content
    )
}