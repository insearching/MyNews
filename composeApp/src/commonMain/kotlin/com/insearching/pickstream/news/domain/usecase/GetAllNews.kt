package com.insearching.pickstream.news.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.domain.repository.RssRepository

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