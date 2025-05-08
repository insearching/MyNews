package com.insearching.pickstream.news.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.insearching.pickstream.core.utils.DateUtils.toUTC
import com.insearching.pickstream.news.domain.model.Story
import kotlinx.serialization.Serializable

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
    @ColumnInfo(name = "guid")
    val guid: String,
    val rssLink: String,
    val title: String,
    val link: String,
    val categories: List<String>,
    val pubDate: String?,
    val description: String?,
    val image: String?,
    val content: String?,
    @ColumnInfo(name = "favorite")
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