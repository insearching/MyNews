package ua.insearching.mynews.news.presentation.notifications

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun NotificationsScreenRoot(
    rootNavController: NavHostController,
    paddingValues: PaddingValues
) {
    NotificationsScreen(
        modifier = Modifier.padding(paddingValues),
    )
}

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {

}