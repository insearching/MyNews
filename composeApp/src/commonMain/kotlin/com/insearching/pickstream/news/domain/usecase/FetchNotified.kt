package com.insearching.pickstream.news.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.domain.repository.RssRepository

class FetchNotified(
    private val rssRepo: RssRepository
) {

    operator fun invoke(): Flow<List<Story>> {
        return rssRepo.fetchNotified()
            .map {
                it.sortedByDescending { it.publicationDate }
            }
    }
}