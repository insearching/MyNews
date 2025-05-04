package com.insearching.pickstream.news.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<ChannelDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(ChannelDatabase.DB_NAME)

        return Room.databaseBuilder<ChannelDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
    }
}