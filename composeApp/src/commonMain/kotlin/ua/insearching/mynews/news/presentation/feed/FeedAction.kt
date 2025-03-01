package ua.insearching.mynews.news.presentation.feed

import ua.insearching.mynews.news.domain.model.Story


sealed interface FeedAction {
    data class OnStoryClick(val story: Story): FeedAction
    data object Refresh : FeedAction
}