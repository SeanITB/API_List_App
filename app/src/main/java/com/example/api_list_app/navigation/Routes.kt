package com.example.api_list_app.navigation

sealed class Routes (val route: String) {
    object LunchScreen: Routes("lunchScreen")
    object MenuScreen: Routes("menueScreen")
    object ListScreen: Routes("listScreen")
    object DetailScreen: Routes("detailScreen")
}