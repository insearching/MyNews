package com.insearching.pickstream.news.presentation.profile

import com.insearching.pickstream.news.domain.model.Channel

sealed interface ProfileAction {
    data class OnFeedAdd(val channel: ChannelUi) : ProfileAction
    data class OnFeedSelected(val channel: Channel) : ProfileAction
    data class OnFeedRemove(val channel: Channel) : ProfileAction
    data class OnFeedNotified(val channel: Channel) : ProfileAction
}