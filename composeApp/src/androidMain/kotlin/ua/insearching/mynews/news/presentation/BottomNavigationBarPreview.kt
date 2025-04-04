package ua.insearching.mynews.news.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ua.insearching.mynews.ui.theme.MyNewsTheme
import ua.insearching.mynews.core.navigation.BottomNavigationBar
import ua.insearching.mynews.core.navigation.navigationItemsLists

@Preview
@Composable
fun BottomNavigationBarPreview() {
    MyNewsTheme {
        BottomNavigationBar(
            currentRoute = "",
            items = navigationItemsLists,
            onItemClick = { _ -> },
        )
    }
}