package ua.insearching.mynews.news.presentation.feed

import ua.insearching.mynews.core.presentation.UiText
import ua.insearching.mynews.news.domain.model.Story

sealed class FeedState {
    data object Loading : FeedState()
    data class Success(val stories: List<Story>) : FeedState()
    data class Error(val message: UiText) : FeedState()
}