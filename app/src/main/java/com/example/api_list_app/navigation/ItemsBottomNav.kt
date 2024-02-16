package com.example.api_list_app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavigationScreens(val route: String, val icon: ImageVector, val label: String) {
    object Favorite : BottomNavigationScreens(Routes.MenuScreen.route, Icons.Filled.Favorite, "Favorite")
    object Home : BottomNavigationScreens(Routes.MenuScreen.route, Icons.Filled.Home, "Home")
    object Settings : BottomNavigationScreens(Routes.MenuScreen.route, Icons.Filled.Settings, "Settings")
}

