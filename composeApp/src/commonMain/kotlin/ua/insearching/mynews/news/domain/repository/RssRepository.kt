package ua.insearching.mynews.news.domain.repository

import com.plcoding.bookpedia.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow
import ua.insearching.mynews.core.domain.DataError
import ua.insearching.mynews.news.domain.model.Channel
import ua.insearching.mynews.news.domain.model.Story
import ua.insearching.mynews.news.presentation.add_feed.ChannelUi

interface RssRepository {

    suspend fun addNewsChannel(channelUi: ChannelUi): EmptyResult<DataError.Local>

    suspend fun removeChannel(url: String): EmptyResult<DataError.Local>

    fun fetchAllChannels(): Flow<List<Channel>>

    fun fetchAllNews(): Flow<List<Story>>

    suspend fun updateNewsFeed(): EmptyResult<DataError>
}