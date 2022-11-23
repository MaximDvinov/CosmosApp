package com.cosmos.app.navigation

sealed class Screens (val title: String, val route: String, val icon: Int? = null){
    object RandomScreen: Screens(title = "Random", route = "random")
    object ListImageScreen: Screens(title = "ListImage", route = "list_image")
}