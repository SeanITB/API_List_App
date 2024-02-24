package com.example.api_list_app.navigation

sealed class Routes (val route: String) {
    object LunchScreen: Routes("lunchScreen")
    object HomeScreen: Routes("homeScreen")
    object ListScreen: Routes("listScreen?lastScreen={lastScreen}") {
        fun createRouteToList(lastScreen: String?) = "listScreen?listScreen={lastScreen}"
    }
    object FavoriteScreen: Routes("favoriteScreen")
    object DetailScreen: Routes("detailScreen?lastScreen={lastScreen}") {
        fun createRouteToDetail(lastScreen: String?) = "detailScreen?lastScreen=$lastScreen"
    }
    object SettingdSreen: Routes("detailScreen")
    object LiberiScreen: Routes("liberiScreen")
}