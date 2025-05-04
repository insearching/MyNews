package com.insearching.pickstream.news.domain.usecase

import com.insearching.pickstream.core.domain.DataError
import com.insearching.pickstream.news.domain.repository.RssRepository
import com.insearching.pickstream.news.presentation.profile.ChannelUi
import com.plcoding.bookpedia.core.domain.EmptyResult

class AddFeedChannel(
    private val rssRepository: RssRepository
) {
    suspend operator fun invoke(channelUi: ChannelUi): EmptyResult<DataError> {
        return rssRepository.addNewsChannel(channelUi)
    }
}