package com.cosmos.app.screen.apod

import androidx.compose.foundation.ScrollState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val randomScreenRoute = "random_screen"

fun NavController.navigateToApodScreen() {
    this.navigate(randomScreenRoute)
}

fun NavGraphBuilder.apodScreen(
    scrollState: ScrollState,
    onClickImage: (url: String) -> Unit
) {
    composable(randomScreenRoute) {
        ApodScreen(scrollState = scrollState,onClickImage = onClickImage)
    }
}