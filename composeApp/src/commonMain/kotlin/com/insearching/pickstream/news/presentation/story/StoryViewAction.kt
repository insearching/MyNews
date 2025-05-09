package com.insearching.pickstream.news.presentation.story


sealed interface StoryViewAction {
    data object MarkUnmarkFavorite : StoryViewAction
}