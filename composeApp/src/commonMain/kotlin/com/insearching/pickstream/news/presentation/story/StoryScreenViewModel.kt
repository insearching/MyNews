package com.insearching.pickstream.news.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.domain.repository.RssRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StoryScreenViewModel(
    private val rssRepository: RssRepository
) : ViewModel() {

    private var story: Story? = null

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun initialize(story: Story) {
        this.story = story
        viewModelScope.launch {
            _isFavorite.value = rssRepository.isFavorite(story.guid)
        }
    }

    fun onAction(action: StoryViewAction) {
        when (action) {
            is StoryViewAction.MarkUnmarkFavorite -> {
                viewModelScope.launch {
                    val story = story ?: return@launch
                    if (rssRepository.markUnmarkFavorite(story.guid, !_isFavorite.value)) {
                        _isFavorite.update { !_isFavorite.value }
                    }
                }
            }
        }
    }
}