package ua.insearching.mynews.app

import kotlinx.serialization.Serializable
import ua.insearching.mynews.news.domain.model.Story

sealed interface Route {

    @Serializable
    data object FeedScreen : Route


    @Serializable
    data object AddFeedScreen : Route

    @Serializable
    data class StoryScreen(val guid: String): Route
}