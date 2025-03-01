package ua.insearching.mynews.news.domain.usecase

import kotlinx.coroutines.flow.Flow
import ua.insearching.mynews.news.domain.model.Channel
import ua.insearching.mynews.news.domain.repository.RssRepository

class GetAllChannels(
    private val rssRepo: RssRepository
) {
    operator fun invoke(): Flow<List<Channel>> {
        return rssRepo.fetchAllChannels()
    }
}