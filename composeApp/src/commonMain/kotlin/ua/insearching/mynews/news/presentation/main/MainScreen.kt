@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package ua.insearching.mynews.news.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.insearching.mynews.core.navigation.graphs.RootNavGraph
import ua.insearching.mynews.core.navigation.NavigationItem
import ua.insearching.mynews.core.navigation.navigationItemsLists
import ua.insearching.mynews.core.navigation.BottomNavigationBar
import ua.insearching.mynews.core.navigation.NavigationSideBar


@Composable
fun MainScreen() {
    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }
    val navigationItem by remember {
        derivedStateOf {
            navigationItemsLists.find { it.route == currentRoute }
        }
    }
    val isMainScreenVisible by remember(isMediumExpandedWWSC) {
        derivedStateOf {
            navigationItem != null
        }
    }
    val isBottomBarVisible by remember(isMediumExpandedWWSC) {
        derivedStateOf {
            if (!isMediumExpandedWWSC) {
                navigationItem != null
            } else {
                false
            }
        }
    }
    MainScaffold(
        rootNavController = rootNavController,
        currentRoute = currentRoute,
        isMediumExpandedWWSC = isMediumExpandedWWSC,
        isBottomBarVisible = isBottomBarVisible,
        isMainScreenVisible = isMainScreenVisible,
        onItemClick = { currentNavigationItem ->
            rootNavController.navigate(currentNavigationItem.route) {
                popUpTo(rootNavController.graph.startDestinationRoute ?: "") {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
fun MainScaffold(
    rootNavController: NavHostController,
    currentRoute: String?,
    isMediumExpandedWWSC: Boolean,
    isBottomBarVisible: Boolean,
    isMainScreenVisible: Boolean,
    onItemClick: (NavigationItem) -> Unit,
) {
    Row {
        AnimatedVisibility(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            visible = isMediumExpandedWWSC && isMainScreenVisible,
            enter = slideInHorizontally(
                // Slide in from the left
                initialOffsetX = { fullWidth -> -fullWidth }
            ),
            exit = slideOutHorizontally(
                // Slide out to the right
                targetOffsetX = { fullWidth -> -fullWidth }
            )
        ) {
            NavigationSideBar(
                items = navigationItemsLists,
                currentRoute = currentRoute,
                onItemClick = { currentNavigationItem ->
                    onItemClick(currentNavigationItem)
                }
            )
        }
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isBottomBarVisible,
                    enter = slideInVertically(
                        // Slide in from the bottom
                        initialOffsetY = { fullHeight -> fullHeight }
                    ),
                    exit = slideOutVertically(
                        // Slide out to the bottom
                        targetOffsetY = { fullHeight -> fullHeight }
                    )
                ) {
                    BottomNavigationBar(
                        items = navigationItemsLists,
                        currentRoute = currentRoute,
                        onItemClick = { currentNavigationItem ->
                            onItemClick(currentNavigationItem)
                        }
                    )
                }
            }
        ) { innerPadding ->
            RootNavGraph(
                rootNavController = rootNavController,
                innerPadding = innerPadding,
            )
        }
    }
}