package com.insearching.pickstream.news.domain.usecase

import FEED_UPDATE_INTERVAL
import com.insearching.pickstream.news.domain.repository.RssRepository
import kotlinx.coroutines.delay

class UpdateFeeds(
    private val rssRepo: RssRepository
) {
    suspend operator fun invoke() {
        while (true) {
            rssRepo.updateFeed()
            delay(FEED_UPDATE_INTERVAL)
        }
    }
}