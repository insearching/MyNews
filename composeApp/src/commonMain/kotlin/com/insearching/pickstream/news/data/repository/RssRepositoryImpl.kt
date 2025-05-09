package com.insearching.pickstream.news.data.repository

import androidx.sqlite.SQLiteException
import com.diamondedge.logging.logging
import com.insearching.pickstream.core.domain.DataError
import com.insearching.pickstream.news.data.database.dao.ChannelDao
import com.insearching.pickstream.news.data.database.dao.StoryDao
import com.insearching.pickstream.news.data.database.entity.ChannelEntity
import com.insearching.pickstream.news.data.database.entity.toEntity
import com.insearching.pickstream.news.domain.model.Channel
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.domain.model.toChannel
import com.insearching.pickstream.news.domain.model.toStory
import com.insearching.pickstream.news.domain.repository.RssRepository
import com.insearching.pickstream.news.presentation.profile.ChannelUi
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.model.RssChannel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RssRepositoryImpl(
    private val rssParser: RssParser,
    private val channelDao: ChannelDao,
    private val storyDao: StoryDao,
    private val httpClient: HttpClient
) : RssRepository {

    override suspend fun addNewsChannel(channelUi: ChannelUi): EmptyResult<DataError> {
        return try {
            fetchRssFeed(channelUi.url)
                ?.toChannel()
                ?.let { channel ->
                    channelDao.upsertChannel(
                        channel.copy(
                            link = channelUi.url
                        ).toEntity()
                    )
                    Result.Success(Unit)
                }
                ?: let {
                    Result.Error(DataError.Remote.SERIAlIZATION)
                }
        } catch (ex: SQLiteException) {
            log.i { ex.message }
            Result.Error(DataError.Local.DATA_ERROR)
        }
    }

    override suspend fun removeChannel(url: String): EmptyResult<DataError.Local> {
        return try {
            channelDao.deleteChannelByUrl(url)
            Result.Success(Unit)
        } catch (ex: SQLiteException) {
            log.i { ex.message }
            Result.Error(DataError.Local.DATA_ERROR)
        }
    }

    override fun fetchAllChannels(): Flow<List<Channel>> = flow {
        val channels = channelDao.getChannels()
        if (channels.isEmpty()) {
            emit(emptyList())
        }

        val newsFeed = channelDao.observeFeed()
            .map {
                it.map { channel ->
                    channel.toChannel()
                }
            }
        emitAll(newsFeed)
    }


    override fun fetchAllNews(): Flow<List<Story>> = flow {
        val newsFeed = channelDao.observeFeed()
            .map {
                it.map { channel ->
                    channel.toChannel().stories.map { story ->
                        story.copy(
                            channelName = channel.title,
                            channelImage = channel.imageUrl
                        )
                    }
                }.flatten()
            }
        emitAll(newsFeed)
    }

    override fun fetchNotified(): Flow<List<Story>> = flow {
        val favoritesFeed = channelDao.observeNotified()
            .map {
                it.map { channel ->
                    channel.toChannel().stories.map { story ->
                        story.copy(
                            channelName = channel.title,
                            channelImage = channel.imageUrl
                        )
                    }
                }.flatten()
            }
        emitAll(favoritesFeed)
    }

    override suspend fun fetchFavorites(): List<Story> = withContext(Dispatchers.IO) {
        storyDao.getFavorites().map { it.toStory() }
    }

    override suspend fun updateFeed(): EmptyResult<DataError> {
        return try {
            val result = channelDao.getChannels()
                .mapNotNull { channel ->
                    fetchRssFeed(channel.link)?.mapToEntity(channel)
                }
                .onEach {
                    channelDao.upsertChannel(it)
                }
            if (result.isNotEmpty()) {
                Result.Success(Unit)
            } else {
                Result.Error(DataError.Remote.SERIAlIZATION)
            }
        } catch (ex: SQLiteException) {
            log.i { ex.message }
            Result.Error(DataError.Local.DATA_ERROR)
        }
    }

    override suspend fun updateNotifiedChannels(): EmptyResult<DataError> {
        return try {
            val result = channelDao.getNotifiedChannels()
                .mapNotNull { channel -> fetchRssFeed(channel.link)?.mapToEntity(channel) }
                .onEach { channelDao.upsertChannel(it) }
            if (result.isNotEmpty()) {
                Result.Success(Unit)
            } else {
                Result.Error(DataError.Remote.SERIAlIZATION)
            }
        } catch (ex: SQLiteException) {
            log.i { ex.message }
            Result.Error(DataError.Local.DATA_ERROR)
        }
    }

    override suspend fun markUnmarkNotified(channel: Channel) {
        channelDao.upsertChannel(channel.toEntity())
    }

    override suspend fun markUnmarkFavorite(guid: String, favorite: Boolean): Boolean {
        return try {
            channelDao.getChannels().map {
                it.stories.firstOrNull { it.guid == guid }
            }.firstOrNull()?.let { story ->
                if (favorite) {
                    storyDao.addStoryToFavorites(story)
                } else {
                    storyDao.deleteStoryFromFavorites(story)
                }
            }
            true
        } catch (ex: Exception) {
            log.i { "Error message -  ${ex.message}" }
            false
        }
    }

    override suspend fun isFavorite(guid: String): Boolean {
        return try {
            storyDao.getStory(guid) != null
        } catch (ex: Exception) {
            log.i { "Error message -  ${ex.message}" }
            false
        }
    }

    private suspend fun fetchRssFeed(url: String): RssChannel? {
        return try {
            val xmlFeed = httpClient.get(url).bodyAsText()
            rssParser.parse(xmlFeed)
        } catch (ex: RssParsingException) {
            log.i { "Failed to parse RSS feed: ${ex.message.toString()}" }
            null
        } catch (ex: Exception) {
            log.i { ex.message }
            null
        }
    }

    private fun RssChannel.mapToEntity(oldEntity: ChannelEntity): ChannelEntity {
        return toChannel()
            .toEntity()
            .copy(
                title = oldEntity.title,
                link = oldEntity.link,
                isNotified = oldEntity.isNotified
            )
    }

    companion object {
        val log = logging()
    }
}