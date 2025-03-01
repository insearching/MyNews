package ua.insearching.mynews.news.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {

    @Upsert
    suspend fun upsertChannel(channel: ChannelEntity)

    @Query("SELECT * FROM ChannelEntity")
    suspend fun getChannels(): List<ChannelEntity>

    @Query("SELECT * FROM ChannelEntity")
    fun observeFeed(): Flow<List<ChannelEntity>>

    @Query("DELETE FROM ChannelEntity WHERE link = :link")
    suspend fun deleteChannelByUrl(link: String)
}