package ua.insearching.mynews.news.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.insearching.mynews.news.domain.model.Story
import ua.insearching.mynews.news.domain.repository.RssRepository

class GetAllNews(
    private val rssRepo: RssRepository
) {
    operator fun invoke(): Flow<List<Story>> {
        return rssRepo.fetchAllNews()
            .map {
                it.sortedByDescending { it.publicationDate }
            }
    }
}