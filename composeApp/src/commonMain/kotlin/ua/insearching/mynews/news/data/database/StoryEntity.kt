package ua.insearching.mynews.news.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import ua.insearching.mynews.core.utils.DateUtils.toUTC
import ua.insearching.mynews.news.domain.model.Story

@Entity(
    foreignKeys = [ForeignKey(
        entity = ChannelEntity::class,
        parentColumns = arrayOf("link"),
        childColumns = arrayOf("rssLink"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Serializable
data class StoryEntity(
    @PrimaryKey(autoGenerate = false)
    val guid: String,
    val rssLink: String,
    val title: String,
    val link: String,
    val categories: List<String>,
    val pubDate: String?,
    val description: String?,
    val image: String?,
    val content: String?
)

fun Story.toEntity(rssLink: String): StoryEntity {
    return StoryEntity(
        guid = guid,
        rssLink = rssLink,
        title = title,
        link = link,
        categories = categories,
        pubDate = publicationDate?.toUTC(),
        description = description,
        image = image,
        content = content
    )
}