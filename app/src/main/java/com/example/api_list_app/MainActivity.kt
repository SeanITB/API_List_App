package com.example.api_list_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.ui.theme.API_List_AppTheme
import com.example.api_list_app.view.DetailScreen
import com.example.api_list_app.view.LunchScreen
import com.example.api_list_app.view.MenuScreen
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
                        startDestination = Routes.DetailScreen.route,
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
                        composable(Routes.MenuScreen.route,) { MenuScreen(navController, booksVM)}
                        composable(Routes.ListScreen.route) { MyRecyclerBooksView(navController, booksVM) }
                        composable(Routes.DetailScreen.route) { DetailScreen(navController, booksVM) }
                    }
                }
            }
        }
    }
}







