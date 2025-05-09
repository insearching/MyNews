package com.insearching.pickstream.news.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import com.insearching.pickstream.news.data.database.entity.CHANNEL_TABLE_NAME
import com.insearching.pickstream.news.data.database.entity.ChannelEntity
import com.insearching.pickstream.news.data.database.entity.STORY_TABLE_NAME
import com.insearching.pickstream.news.data.database.entity.StoryEntity

@Dao
interface ChannelDao {

    @Upsert
    suspend fun upsertChannel(channel: ChannelEntity)

    @Query("SELECT * FROM $CHANNEL_TABLE_NAME")
    suspend fun getChannels(): List<ChannelEntity>

    @Query("SELECT * FROM $CHANNEL_TABLE_NAME WHERE isNotified = 1")
    suspend fun getNotifiedChannels(): List<ChannelEntity>

    @Query("SELECT * FROM $CHANNEL_TABLE_NAME")
    fun observeFeed(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM $CHANNEL_TABLE_NAME WHERE isNotified = 1")
    fun observeNotified(): Flow<List<ChannelEntity>>

    @Query("DELETE FROM $CHANNEL_TABLE_NAME WHERE link = :link")
    suspend fun deleteChannelByUrl(link: String)
}