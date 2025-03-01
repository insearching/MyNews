package ua.insearching.mynews.news.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<ChannelDatabase>
}