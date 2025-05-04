package com.insearching.pickstream.news.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.insearching.pickstream.news.domain.model.Story
import com.insearching.pickstream.news.presentation.home.StoryItem

@Composable
fun FeedContent(
    modifier: Modifier = Modifier,
    stories: List<Story>,
    onStorySelected: (Story) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(stories, key = { it.guid }) {
            StoryItem(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                story = it,
                onClick = {
                    onStorySelected(it)
                }
            )
        }
    }
}