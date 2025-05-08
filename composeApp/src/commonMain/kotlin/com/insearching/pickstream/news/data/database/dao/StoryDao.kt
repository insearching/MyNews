package com.insearching.pickstream.news.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.insearching.pickstream.news.data.database.entity.STORY_TABLE_NAME
import com.insearching.pickstream.news.data.database.entity.StoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface StoryDao {

    @Query("SELECT * FROM $STORY_TABLE_NAME WHERE favorite = 1")
    fun getFavorites(): Flow<List<StoryEntity>>

    @Query("SELECT * FROM $STORY_TABLE_NAME WHERE guid = :guid")
    suspend fun getStory(guid: String): StoryEntity?

    @Upsert
    suspend fun upsertStory(entity: StoryEntity)
}