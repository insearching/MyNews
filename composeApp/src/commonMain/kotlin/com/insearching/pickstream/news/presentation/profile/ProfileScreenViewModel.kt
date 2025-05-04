package com.insearching.pickstream.news.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.pickstream.core.presentation.UiText
import com.insearching.pickstream.news.domain.repository.RssRepository
import com.insearching.pickstream.news.domain.usecase.AddFeedChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.no_channels_found

class ProfileScreenViewModel(
    private val addChannel: AddFeedChannel,
    private val rssRepository: RssRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Loading)
    val state = _state
        .onStart { fetchChannels() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _state.value
        )

    private val _message = Channel<UiText>()
    val message = _message.receiveAsFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnFeedSelected -> {

            }

            is ProfileAction.OnFeedAdd -> {
                viewModelScope.launch {
                    addChannel(action.channel)
                }
            }

            is ProfileAction.OnFeedRemove -> {
                viewModelScope.launch {
                    rssRepository.removeChannel(action.channel.link)
                }
            }

            is ProfileAction.OnFeedNotified -> {
                viewModelScope.launch {
                    rssRepository.markUnmarkNotified(action.channel.copy(isNotified = !action.channel.isNotified))
                }
            }
        }
    }

    private fun fetchChannels() = viewModelScope.launch {
        _state.update {
            ProfileScreenState.Loading
        }

        rssRepository.fetchAllChannels()
            .collectLatest { list ->
                _state.update {
                    if (list.isEmpty()) {
                        ProfileScreenState.Error(UiText.StringResourceId(Res.string.no_channels_found))
                    } else {
                        ProfileScreenState.Success(list)
                    }
                }
            }
    }
}