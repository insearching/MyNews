package com.insearching.pickstream.news.domain.repository

import com.insearching.pickstream.core.domain.DataError
import com.insearching.pickstream.news.domain.model.Channel
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.presentation.profile.ChannelUi
import com.plcoding.bookpedia.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow

interface RssRepository {

    suspend fun addNewsChannel(channelUi: ChannelUi): EmptyResult<DataError>
    suspend fun removeChannel(url: String): EmptyResult<DataError.Local>
    fun fetchAllChannels(): Flow<List<Channel>>
    fun fetchAllNews(): Flow<List<Story>>
    fun fetchNotified(): Flow<List<Story>>
    fun fetchFavorites(): Flow<List<Story>>
    suspend fun updateFeed(): EmptyResult<DataError>
    suspend fun updateNotifiedChannels(): EmptyResult<DataError>
    suspend fun markUnmarkNotified(channel: Channel)
    suspend fun markUnmarkFavorite(guid: String, favorite: Boolean): Boolean
}