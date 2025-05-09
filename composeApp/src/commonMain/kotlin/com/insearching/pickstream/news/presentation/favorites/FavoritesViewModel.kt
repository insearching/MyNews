package com.insearching.pickstream.news.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.pickstream.news.domain.repository.RssRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val rssRepository: RssRepository
) : ViewModel() {

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

        val favorites = rssRepository.fetchFavorites()
        _state.update {
            FavoritesViewState.Success(favorites)
        }
    }
}