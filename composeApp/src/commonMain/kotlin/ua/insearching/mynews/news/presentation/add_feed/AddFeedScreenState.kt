package ua.insearching.mynews.news.presentation.add_feed

import ua.insearching.mynews.core.presentation.UiText
import ua.insearching.mynews.news.domain.model.Channel

sealed interface AddFeedScreenState {
    data object Loading : AddFeedScreenState
    data class Success(val channels: List<Channel>) : AddFeedScreenState
    data class Error(val message: UiText) : AddFeedScreenState
}