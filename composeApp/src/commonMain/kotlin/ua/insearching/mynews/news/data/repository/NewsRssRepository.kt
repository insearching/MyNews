package ua.insearching.mynews.news.data.repository

import androidx.sqlite.SQLiteException
import com.diamondedge.logging.logging
import ua.insearching.mynews.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.model.RssChannel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ua.insearching.mynews.news.data.database.ChannelDao
import ua.insearching.mynews.news.data.database.toEntity
import ua.insearching.mynews.news.domain.model.Channel
import ua.insearching.mynews.news.domain.model.Story
import ua.insearching.mynews.news.domain.model.toChannel
import ua.insearching.mynews.news.domain.repository.RssRepository
import ua.insearching.mynews.news.presentation.profile.ChannelUi

class NewsRssRepository(
    private val rssParser: RssParser,
    private val channelDao: ChannelDao,
    private val httpClient: HttpClient
) : RssRepository {

    override suspend fun addNewsChannel(channelUi: ChannelUi): EmptyResult<DataError.Local> {
        return try {
            fetchRssFeed(channelUi.url)?.toChannel()?.let { channel ->
                channelDao.upsertChannel(
                    channel.copy(
                        title = channelUi.name,
                        link = channelUi.url
                    ).toEntity()
                )
            } ?: run {
                Result.Error(DataError.Remote.SERIAlIZATION)
            }
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DATA_ERROR)
        }
    }

    override suspend fun removeChannel(url: String): EmptyResult<DataError.Local> {
        return try {
            channelDao.deleteChannelByUrl(url)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
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
        val channels = channelDao.getChannels()
        if (channels.isEmpty()) {
            emit(emptyList())
        }

        val newsFeed = channelDao.observeFeed()
            .map {
                it.map { channel ->
                    channel.toChannel().stories.map { it.copy(channelName = channel.title) }
                }.flatten()
            }
        emitAll(newsFeed)
    }

    override suspend fun updateNewsFeed(): EmptyResult<DataError> {
        return try {
            val result = channelDao.getChannels()
                .mapNotNull {
                    fetchRssFeed(it.link)?.toChannel()
                        ?.toEntity()
                        ?.copy(
                            title = it.title,
                            link = it.link
                        )
                }
                .onEach {
                    channelDao.upsertChannel(it)
                }
            if (result.isNotEmpty()) {
                Result.Success(Unit)
            } else {
                Result.Error(DataError.Remote.SERIAlIZATION)
            }
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DATA_ERROR)
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
            log.i { ex.message.toString() }
            null
        }
    }

    companion object {
        val log = logging()
    }
}