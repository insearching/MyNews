package ua.insearching.mynews.news.presentation.add_feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mynews.composeapp.generated.resources.Res
import mynews.composeapp.generated.resources.no_channels_found
import ua.insearching.mynews.core.presentation.UiText
import ua.insearching.mynews.news.domain.repository.RssRepository
import ua.insearching.mynews.news.domain.usecase.AddNewChannel
import ua.insearching.mynews.news.domain.usecase.GetAllChannels

class AddFeedScreenViewModel(
    private val getAllChannels: GetAllChannels,
    private val addChannel: AddNewChannel,
    private val rssRepository: RssRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AddFeedScreenState>(AddFeedScreenState.Loading)
    val state = _state
        .onStart { fetchChannels() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)


    fun onAction(action: AddFeedAction) {
        when(action){
            is AddFeedAction.OnFeedSelected -> {

            }
            is AddFeedAction.OnFeedAdd -> {
                viewModelScope.launch {
                    addChannel(action.channel)
                }
            }

            is AddFeedAction.OnFeedRemove -> {
                viewModelScope.launch {
                    rssRepository.removeChannel(action.channel.link)
                }
            }
        }
    }

    private fun fetchChannels() = viewModelScope.launch {
        _state.update {
            AddFeedScreenState.Loading
        }

        getAllChannels()
            .collectLatest { list ->
                _state.update {
                    if (list.isEmpty()) {
                        AddFeedScreenState.Error(UiText.StringResourceId(Res.string.no_channels_found))
                    } else {
                        AddFeedScreenState.Success(list)
                    }
                }
            }
    }
}