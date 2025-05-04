package com.insearching.pickstream.news.presentation.home

import com.insearching.pickstream.core.presentation.UiText
import com.insearching.pickstream.news.domain.model.Story

sealed class HomeViewState {
    data object Loading : HomeViewState()
    data class Success(val stories: List<Story>) : HomeViewState()
    data class Error(val message: UiText) : HomeViewState()
}