package com.dvinov.myspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dvinov.myspaceapp.navigation.NavigationGraph
import com.dvinov.myspaceapp.ui.theme.CosmosAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setUpEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
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
