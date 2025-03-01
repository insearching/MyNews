package ua.insearching.mynews.news.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.insearching.mynews.news.domain.model.Channel

@Entity
data class ChannelEntity(
    @PrimaryKey(autoGenerate = false)
    val link: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val stories: List<StoryEntity> = emptyList()
)

fun Channel.toEntity(): ChannelEntity {
    return ChannelEntity(
        link = link,
        title = title,
        description = description,
        imageUrl = imageUrl,
        stories = stories.map { it.toEntity(this.link) }
    )
}