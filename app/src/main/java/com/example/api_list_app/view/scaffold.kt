package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController, booksVM: BocksViewModel, actualScreen: String, title: CharSequence) {
    val books: Data by booksVM.books.observeAsState(Data(emptyList(), "", 0))
    val favorites: MutableList<BookDetail> by booksVM.favorites.observeAsState(mutableListOf())
    val toRead: MutableList<BookDetail> by booksVM.toRead.observeAsState(mutableListOf())
    //val searchBooks: List<Book> = booksVM.getSearchBooks()
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )

    Scaffold (
        topBar = {MyTopAppBarList(navController = navController, booksVM = booksVM)},
        bottomBar = { MyBottomBar(navController = navController, bottomNavItems = bottomNavigationItems)}
    ) { paddingValues ->
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {

            when (actualScreen) {
                "favoriteScreen" -> {
                    items(favorites) { book ->
                        println("fav")
                        BookItem(navController, book, booksVM )
                    }
                }
                "listScreen", "search" -> {
                    items(books.books) { book ->
                        println("listScreen")
                        BookItem(navController, book, booksVM)
                    }
                }
                /*
                "search" -> {
                    println("He entrado")
                    items(searchBooks) {book ->
                        println("libro")
                        BookItem(navController = navController, book = book, booksVM = booksVM)
                    }
                }

                 */
                else -> {
                    items(toRead) {book ->
                        BookItem(navController, book, booksVM)
                    }
                }
            }

            /*
            items(
                when(actualScreen) {
                    "favoriteScreen" -> favorites
                    "listScreen" -> books.books
                    "search" -> searchBooks
                    else -> toRead
                }
            ) { book ->
                println("fav")
                BookItem(navController, book, booksVM)
            }

             */
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarList(navController: NavController, booksVM: BocksViewModel) {
    val isSearch: Boolean by booksVM.isSearching.observeAsState(false)
    TopAppBar(
        title = { Text(text = booksVM.title ) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        actions = {
            IconButton(onClick = {
                booksVM.changeIsSearching(true)
                navController.navigate(Routes.ListScreen.route)
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
            }
            IconButton(onClick = { /*toDo*/ }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.background)
            }
        }
    )
    if (isSearch) MySearchBar(navController, booksVM)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(navController: NavController, booksVM: BocksViewModel) {
    val serchText by booksVM.searchText.observeAsState("")
    SearchBar(
        query = serchText,
        onQueryChange = { booksVM.onSearchTextChange(it) },
        onSearch = { booksVM.onSearchTextChange(it) },
        active = true,
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")},
        trailingIcon = { Icon(imageVector = Icons.Filled.Close, contentDescription = "Close search", modifier = Modifier.clickable { booksVM.changeIsSearching(false) })},
        placeholder = { Text("What are you looking for?")},
        onActiveChange = {},
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .clip(CircleShape),
    ){}
}










