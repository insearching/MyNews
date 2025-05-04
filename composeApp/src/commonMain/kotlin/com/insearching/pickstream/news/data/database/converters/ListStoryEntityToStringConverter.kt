package com.insearching.pickstream.news.data.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.insearching.pickstream.news.data.database.entity.StoryEntity

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