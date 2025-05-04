package com.insearching.pickstream.news.presentation.favorites

import com.insearching.pickstream.news.domain.model.Story


sealed interface FavoritesViewAction {
    data class OnStoryClick(val story: Story) : FavoritesViewAction
    data class MarkUnmarkFavorite(val story: Story) : FavoritesViewAction
}