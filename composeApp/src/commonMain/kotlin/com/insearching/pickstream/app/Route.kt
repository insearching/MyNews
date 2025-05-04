package com.insearching.pickstream.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object FeedScreen : Route


    @Serializable
    data object AddFeedScreen : Route

    @Serializable
    data class StoryScreen(val guid: String): Route
}