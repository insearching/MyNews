package ua.insearching.mynews.news.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mynews.composeapp.generated.resources.Res
import mynews.composeapp.generated.resources.no_news_found
import ua.insearching.mynews.core.presentation.UiText
import ua.insearching.mynews.news.domain.usecase.GetAllNews
import ua.insearching.mynews.news.domain.usecase.UpdateNews

class FeedViewModel(
    private val getAllNews: GetAllNews,
    private val updateNews: UpdateNews
) : ViewModel() {

    private val _state = MutableStateFlow<FeedState>(FeedState.Loading)
    val state = _state
        .onStart { fetchNews() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    private fun fetchNews() = viewModelScope.launch {
        launch { updateNews() }

        _state.update {
            FeedState.Loading
        }

        getAllNews()
            .collectLatest { list ->
                _state.update {
                    if (list.isEmpty()) {
                        FeedState.Error(UiText.StringResourceId(Res.string.no_news_found))
                    } else {
                        FeedState.Success(list)
                    }
                }
            }
    }

    fun onAction(action: FeedAction) {

    }
}