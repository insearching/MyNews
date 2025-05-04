package com.insearching.pickstream.news.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.pickstream.core.presentation.UiText
import com.insearching.pickstream.news.domain.usecase.AddFeedChannel
import com.insearching.pickstream.news.domain.usecase.GetAllNews
import com.insearching.pickstream.news.domain.usecase.UpdateFeeds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.add_feed

class HomeViewModel(
    private val getAllNews: GetAllNews,
    private val updateFeeds: UpdateFeeds,
    private val addFeed: AddFeedChannel
) : ViewModel() {

    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val state = _state
        .onStart { fetchNews() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    private fun fetchNews() = viewModelScope.launch {
        launch { updateFeeds() }

        _state.update {
            HomeViewState.Loading
        }

        getAllNews()
            .collectLatest { list ->
                _state.update {
                    if (list.isEmpty()) {
                        HomeViewState.Error(UiText.StringResourceId(Res.string.add_feed))
                    } else {
                        HomeViewState.Success(list)
                    }
                }
            }
    }

    fun onAction(action: HomeViewAction) {
        when (action) {
            is HomeViewAction.OnStoryClick -> {}
            HomeViewAction.Refresh -> {
                fetchNews()
            }

            is HomeViewAction.OnAddFeed -> {
                viewModelScope.launch {
                    addFeed(action.channel)
                }
            }
        }
    }
}