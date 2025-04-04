package ua.insearching.mynews.news.presentation.home

import ua.insearching.mynews.core.presentation.UiText
import ua.insearching.mynews.news.domain.model.Story

sealed class HomeViewState {
    data object Loading : HomeViewState()
    data class Success(val stories: List<Story>) : HomeViewState()
    data class Error(val message: UiText) : HomeViewState()
}