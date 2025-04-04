package ua.insearching.mynews.news.presentation.home

import ua.insearching.mynews.news.domain.model.Story


sealed interface HomeViewAction {
    data class OnStoryClick(val story: Story): HomeViewAction
    data object Refresh : HomeViewAction
}