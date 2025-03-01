package ua.insearching.mynews.news.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ChannelEntity::class
    ],
    version = 1
)
@TypeConverters(
    ListStoryEntityToStringConverter::class
)
@ConstructedBy(ChannelDatabaseConstructor::class)
abstract class ChannelDatabase : RoomDatabase() {
    abstract val channelDao: ChannelDao

    companion object {
        const val DB_NAME = "channel.db"
    }
}