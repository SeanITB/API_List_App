package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarList(navController: NavController, booksVM: BocksViewModel, title: CharSequence) {
    TopAppBar(
        title = { Text(text = "$title books" ) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.background)
            }
        },
        actions = {
            IconButton(onClick = { /*toDo*/ }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
            }
            IconButton(onClick = { /*toDo*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.background)
            }
        }
    )
}

@Composable
fun MyBottomBar(navController: NavController, bottomNavItems:  List<BottomNavigationScreens>) {
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.background) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label, tint = MaterialTheme.colorScheme.background) },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black,
                alwaysShowLabel = false,
                onClick = {
                    if (currentRoute != item.route)
                        navController.navigate(item.route)
                }
            )
        }
    }
}










