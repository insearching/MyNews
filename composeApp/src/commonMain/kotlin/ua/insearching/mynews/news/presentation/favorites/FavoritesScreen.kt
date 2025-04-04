package ua.insearching.mynews.news.presentation.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FavoritesScreenRoot(
    rootNavController: NavHostController,
    paddingValues: PaddingValues
) {
    FavoritesScreen(
        modifier = Modifier.padding(paddingValues),
    )
}

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
) {

}