package com.cosmos.app.screen.random

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val randomScreenRoute = "random_screen"

fun NavController.navigateToRandomScreen() {
    this.navigate(randomScreenRoute)
}

fun NavGraphBuilder.randomScreen(onClickImage: (url: String) -> Unit) {
    composable(randomScreenRoute) {
        RandomScreen(onClickImage = onClickImage)
    }
}