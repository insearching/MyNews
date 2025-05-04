package com.insearching.pickstream.news.presentation.profile

import com.insearching.pickstream.core.presentation.UiText
import com.insearching.pickstream.news.domain.model.Channel

sealed interface ProfileScreenState {
    data object Loading : ProfileScreenState
    data class Success(val channels: List<Channel>) : ProfileScreenState
    data class Error(val message: UiText) : ProfileScreenState
}