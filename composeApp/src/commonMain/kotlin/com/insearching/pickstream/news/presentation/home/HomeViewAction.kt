package com.insearching.pickstream.news.presentation.home

import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.presentation.profile.ChannelUi


sealed interface HomeViewAction {
    data class OnStoryClick(val story: Story) : HomeViewAction
    data object Refresh : HomeViewAction
    data class OnAddFeed(val channel: ChannelUi): HomeViewAction
}