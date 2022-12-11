package com.cosmos.app.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cosmos.app.screen.image.imageScreen
import com.cosmos.app.screen.apod.apodScreen
import com.cosmos.app.screen.apod.apodScreenRoute

@Composable
fun NavigationGraph(
    scrollState: ScrollState,
    modifier: Modifier, navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = apodScreenRoute,
        modifier = modifier,
    ) {
        apodScreen(scrollState, navController)

        imageScreen() {
            navController.popBackStack()
        }
    }
}