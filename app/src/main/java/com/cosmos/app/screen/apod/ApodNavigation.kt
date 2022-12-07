package com.cosmos.app.screen.apod

import androidx.compose.foundation.ScrollState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val randomScreenRoute = "random_screen"

fun NavController.navigateToApodScreen() {
    this.navigate(randomScreenRoute)
}

fun NavGraphBuilder.apodScreen(
    scrollState: ScrollState,
    navController: NavHostController,
) {
    composable(randomScreenRoute) {
        ApodScreen(scrollState = scrollState, navController = navController)
    }
}