package com.insearching.pickstream.news.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.insearching.pickstream.news.data.database.converters.ListStoryEntityToStringConverter
import com.insearching.pickstream.news.data.database.converters.ListStringToStringConverter
import com.insearching.pickstream.news.data.database.dao.ChannelDao
import com.insearching.pickstream.news.data.database.dao.StoryDao
import com.insearching.pickstream.news.data.database.entity.ChannelEntity
import com.insearching.pickstream.news.data.database.entity.StoryEntity

@Database(
    entities = [
        ChannelEntity::class,
        StoryEntity::class
    ],
    version = 1
)
@TypeConverters(
    ListStoryEntityToStringConverter::class,
    ListStringToStringConverter::class
)
@ConstructedBy(ChannelDatabaseConstructor::class)
abstract class ChannelDatabase : RoomDatabase() {
    abstract val channelDao: ChannelDao
    abstract val storyDao: StoryDao

    companion object {
        const val DB_NAME = "channel.db"
    }
}