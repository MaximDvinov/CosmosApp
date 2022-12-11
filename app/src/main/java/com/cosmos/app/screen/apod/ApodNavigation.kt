package com.cosmos.app.screen.apod

import androidx.compose.foundation.ScrollState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.cosmos.app.screen.apod.ui.ApodScreen

const val apodScreenRoute = "random_screen"

fun NavController.navigateToApodScreen() {
    this.navigate(apodScreenRoute)
}

fun NavGraphBuilder.apodScreen(
    scrollState: ScrollState,
    navController: NavHostController,
) {
    composable(apodScreenRoute) {
        ApodScreen(scrollState = scrollState, navController = navController)
    }
}