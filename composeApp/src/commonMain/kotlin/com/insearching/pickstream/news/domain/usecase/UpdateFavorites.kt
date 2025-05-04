package com.insearching.pickstream.news.domain.usecase

import FEED_UPDATE_INTERVAL
import kotlinx.coroutines.delay
import com.insearching.pickstream.news.domain.repository.RssRepository

class UpdateFavorites(
    private val rssRepo: RssRepository
) {
    suspend operator fun invoke() {
        while (true) {
            rssRepo.updateNotifiedChannels()
            delay(FEED_UPDATE_INTERVAL)
        }
    }
}