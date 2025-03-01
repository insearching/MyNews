package ua.insearching.mynews.news.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ListStoryEntityToStringConverter {

    @TypeConverter
    fun fromString(value: String): List<StoryEntity> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromList(list: List<StoryEntity>): String {
        return Json.encodeToString(list)
    }
}