package ua.insearching.mynews.news.presentation.profile

import ua.insearching.mynews.core.presentation.UiText
import ua.insearching.mynews.news.domain.model.Channel

sealed interface ProfileScreenState {
    data object Loading : ProfileScreenState
    data class Success(val channels: List<Channel>) : ProfileScreenState
    data class Error(val message: UiText) : ProfileScreenState
}