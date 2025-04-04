package ua.insearching.mynews.news.presentation.profile

import ua.insearching.mynews.news.domain.model.Channel

sealed interface ProfileAction {
    data class OnFeedAdd(val channel: ChannelUi): ProfileAction
    data class OnFeedSelected(val channel: Channel): ProfileAction
    data class OnFeedRemove(val channel: Channel): ProfileAction
}