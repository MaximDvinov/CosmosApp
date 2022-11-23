package com.cosmos.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cosmos.app.screen.random.RandomScreen

@Composable
fun NavigationGraph(modifier: Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.RandomScreen.route,
        modifier = modifier,
    ) {
        composable(Screens.RandomScreen.route) {
            RandomScreen()
        }
    }
}