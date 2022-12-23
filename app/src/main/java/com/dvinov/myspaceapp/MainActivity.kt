package com.dvinov.myspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dvinov.myspaceapp.navigation.NavigationGraph
import com.dvinov.myspaceapp.ui.theme.CosmosAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val scrollState = rememberScrollState()
            CosmosAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CosmosApp(scrollState, navController)
                }
            }
        }
    }

    @Composable
    private fun CosmosApp(
        scrollState: ScrollState,
        navController: NavHostController
    ) {
        Scaffold {
            NavigationGraph(
                scrollState = scrollState,
                modifier = Modifier.padding(it),
                navController = navController
            )
        }
    }
}

