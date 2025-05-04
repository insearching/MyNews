package com.insearching.pickstream.news.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import com.insearching.pickstream.core.utils.DateUtils.toUTC
import com.insearching.pickstream.news.domain.model.Story

const val STORY_TABLE_NAME = "stories"

@Entity(
    foreignKeys = [ForeignKey(
        entity = ChannelEntity::class,
        parentColumns = arrayOf("link"),
        childColumns = arrayOf("rssLink"),
        onDelete = ForeignKey.CASCADE
    )],
    tableName = STORY_TABLE_NAME
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
    val content: String?,
    val isFavorite: Boolean = false
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
        content = content,
        isFavorite = isFavorite
    )
}