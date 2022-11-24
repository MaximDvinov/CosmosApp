package com.cosmos.app.screen.image

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cosmos.app.screen.random.RandomScreen

const val imageScreenRoute = "image_screen"
const val imageUrlArg = "url"

fun NavController.navigateToImageScreen(url: String) {
    this.navigate("$imageScreenRoute?$imageUrlArg=$url")
}

fun NavGraphBuilder.imageScreen(onBackClick: () -> Unit) {
    composable("$imageScreenRoute?$imageUrlArg={$imageUrlArg}",
        arguments = listOf(
            navArgument(imageUrlArg) { type = StringType }
        )
    ) { backStackEntry ->
        backStackEntry.arguments?.getString(imageUrlArg)?.let {
            ImageScreen(it, onBackClick = onBackClick)
        }
    }
}