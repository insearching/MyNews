package com.insearching.pickstream.news.presentation.favorites

import com.insearching.pickstream.core.presentation.UiText
import com.insearching.pickstream.news.domain.model.Story

sealed class FavoritesViewState {
    data object Loading : FavoritesViewState()
    data class Success(val stories: List<Story>) : FavoritesViewState()
    data class Error(val message: UiText) : FavoritesViewState()
}