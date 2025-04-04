package ua.insearching.mynews.news.domain.usecase

import ua.insearching.mynews.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import ua.insearching.mynews.news.domain.repository.RssRepository
import ua.insearching.mynews.news.presentation.profile.ChannelUi

class AddNewChannel(
    private val rssRepository: RssRepository
) {
    suspend operator fun invoke(channelUi: ChannelUi): EmptyResult<DataError.Local> {
        return rssRepository.addNewsChannel(channelUi)
    }
}