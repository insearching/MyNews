package com.insearching.pickstream.news.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.pickstream.core.presentation.UiText
import com.insearching.pickstream.news.domain.repository.RssRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.add_feed

class FavoritesViewModel(
    private val rssRepository: RssRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _state = MutableStateFlow<FavoritesViewState>(FavoritesViewState.Loading)
    val state = _state
        .onStart { fetchFavorites() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _state.value
        )

    private fun fetchFavorites() = viewModelScope.launch {
        _state.update {
            FavoritesViewState.Loading
        }

        rssRepository.fetchFavorites().collectLatest { result ->
            _state.update {
                if (result.isEmpty()) {
                    FavoritesViewState.Error(UiText.StringResourceId(Res.string.add_feed))
                } else {
                    FavoritesViewState.Success(result)
                }
            }
        }
    }

    fun onAction(action: FavoritesViewAction) {
        when (action) {
            is FavoritesViewAction.MarkUnmarkFavorite -> {
                viewModelScope.launch {
                    val story = action.story
                    if(rssRepository.markUnmarkFavorite(story.guid, !story.isFavorite)){
                        _isFavorite.update {
                            !story.isFavorite
                        }
                    }

                }
            }

            else -> {
                // do nothing
            }
        }
    }
}