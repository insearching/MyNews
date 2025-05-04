package com.insearching.pickstream.news.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insearching.pickstream.news.domain.model.Channel

const val CHANNEL_TABLE_NAME = "channels"

@Entity(tableName = CHANNEL_TABLE_NAME)
data class ChannelEntity(
    @PrimaryKey(autoGenerate = false)
    val link: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val stories: List<StoryEntity> = emptyList(),
    val isNotified: Boolean = false
)

fun Channel.toEntity(): ChannelEntity {
    return ChannelEntity(
        link = link,
        title = title,
        description = description,
        imageUrl = imageUrl,
        stories = stories.map { it.toEntity(this.link) },
        isNotified = isNotified
    )
}