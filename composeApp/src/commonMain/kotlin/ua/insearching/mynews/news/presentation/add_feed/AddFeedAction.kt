package ua.insearching.mynews.news.presentation.add_feed

import ua.insearching.mynews.news.domain.model.Channel

sealed interface AddFeedAction {
    data class OnFeedAdd(val channel: ChannelUi): AddFeedAction
    data class OnFeedSelected(val channel: Channel): AddFeedAction
    data class OnFeedRemove(val channel: Channel): AddFeedAction
}