package ua.insearching.mynews.news.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ChannelDatabaseConstructor: RoomDatabaseConstructor<ChannelDatabase> {
    override fun initialize(): ChannelDatabase
}