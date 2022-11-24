package com.cosmos.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cosmos.app.screen.image.imageScreen
import com.cosmos.app.screen.image.navigateToImageScreen
import com.cosmos.app.screen.random.RandomScreen
import com.cosmos.app.screen.random.randomScreen
import com.cosmos.app.screen.random.randomScreenRoute

@Composable
fun NavigationGraph(modifier: Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = randomScreenRoute,
        modifier = modifier,
    ) {
        randomScreen { url ->
            navController.navigateToImageScreen(url)
        }

        imageScreen(){
            navController.popBackStack()
        }
    }
}