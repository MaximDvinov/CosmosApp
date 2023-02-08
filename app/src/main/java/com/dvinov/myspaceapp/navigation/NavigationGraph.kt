package com.dvinov.myspaceapp.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dvinov.myspaceapp.screen.image.imageScreen
import com.dvinov.myspaceapp.screen.apod.apodScreen
import com.dvinov.myspaceapp.screen.apod.apodScreenRoute

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
        imageScreen(onBackClick = navController::popBackStack)
    }
}