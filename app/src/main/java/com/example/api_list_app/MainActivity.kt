package com.example.api_list_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.ui.theme.API_List_AppTheme
import com.example.api_list_app.view.DetailScreen
import com.example.api_list_app.view.LunchScreen
import com.example.api_list_app.view.MyRecyclerBooksView
import com.example.api_list_app.viewModel.BocksViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booksVM by viewModels<BocksViewModel>()
        val TIME : Int = 1000

        setContent {
            API_List_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.ListScreen.route,
                        enterTransition = {
                            fadeIn(animationSpec = tween(TIME)) + slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left, tween(TIME)
                            )
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(TIME)) + slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down, tween(TIME)
                            )
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(TIME)) + slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up, tween(TIME)
                            )
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(TIME)) + slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right, tween(TIME)
                            )
                        }
                    ) {
                        composable(Routes.LunchScreen.route) { LunchScreen(navController) }
                        //composable(Routes.MenuScreen.route,) { MenuScreen(navController, booksVM)}
                        composable(Routes.ListScreen.route) { MyRecyclerBooksView(navController, booksVM) }
                        composable(Routes.DetailScreen.route) { DetailScreen(navController, booksVM) }
                    }
                }
            }
        }
    }
}







