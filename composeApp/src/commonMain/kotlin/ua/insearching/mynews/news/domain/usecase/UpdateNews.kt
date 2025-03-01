package ua.insearching.mynews.news.domain.usecase

import kotlinx.coroutines.delay
import ua.insearching.mynews.news.domain.repository.RssRepository
import kotlin.time.Duration.Companion.seconds

private val FEED_UPDATE_INTERVAL = 30.seconds

class UpdateNews(
    private val rssRepo: RssRepository
) {
    suspend operator fun invoke() {
        while (true) {
            rssRepo.updateNewsFeed()
            delay(FEED_UPDATE_INTERVAL)
        }
    }
}