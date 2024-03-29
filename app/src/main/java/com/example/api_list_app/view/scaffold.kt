package com.example.api_list_app.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.Data
import com.example.api_list_app.model.ToRead
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.viewModel.BocksViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController, booksVM: BocksViewModel) {
    val books: Data by booksVM.booksByGender.observeAsState(Data(emptyList(), "", 0))
    val favorites: List<Book> by booksVM.favorites.observeAsState(mutableListOf())
    val isSearch: Boolean by booksVM.isSearching.observeAsState(false)
    //val searchBooks: List<Book> = booksVM.getSearchBooks()
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )

    Scaffold(
        topBar = {
            MyTopAppBarList(navController = navController, booksVM = booksVM)
            if (isSearch) MySearchBar(navController, booksVM)
        },
        bottomBar = {
            if (booksVM.actualScreen != "listScreen") {
                MyBottomBar(
                    navController = navController,
                    bottomNavItems = bottomNavigationItems
                )
            }
        }
    ) { paddingValues ->
        if (favorites.isNotEmpty()) {
            ConstraintLayout {
                val (booksCL) = createRefs()
                //Spacer(modifier = Modifier.height(300.dp))
                Box(
                    modifier = Modifier
                        //.fillMaxWidth(0.8f)
                        //.fillMaxHeight(0.8f)
                        .height(if (booksVM.actualScreen != "listScreen") 625.dp else 700.dp)
                        .width(400.dp)
                        .constrainAs(booksCL) {
                            top.linkTo(parent.top, margin = 75.dp)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        content = {
                            items(
                                when (booksVM.actualScreen) {
                                    "favoriteScreen" -> favorites
                                    else -> books.books
                                }
                            ) { book ->
                                BookItem(navController, book, booksVM)
                            }
                        }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Any favorite books for the moment")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarList(navController: NavController, booksVM: BocksViewModel) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = booksVM.title) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier,
        //.fillMaxHeight(0.1f),
        //.padding(1.dp),
        //.weight(1f),
        navigationIcon = {
            if (booksVM.actualScreen.equals("listScreen")) {
                //booksVM.changePreviusScreen(booksVM.actualScreen)
                IconButton(onClick = {
                    navController.navigate(
                        booksVM.getRout()
                    )
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                booksVM.changeIsSearching(true)
                //navController.navigate(Routes.ListScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.background
                )
            }
            IconButton(onClick = { booksVM.changeDispalyMenu(!booksVM.displayMenu)}) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More icon",
                    tint = MaterialTheme.colorScheme.background
                )
            }
            DropdownMenu(expanded = booksVM.displayMenu, onDismissRequest = { booksVM.changeDispalyMenu(false) }) {
                DropdownMenuItem(onClick = {
                    Toast.makeText(context, "Aquí navegaría a una supuesta página de settings.", Toast.LENGTH_LONG).show()
                    booksVM.changeDispalyMenu(false)
                }) {
                    Text(text = "Settigs")
                }
                DropdownMenuItem(onClick = {
                    Toast.makeText(context, "Aquí pondría el drop down menu, que ahora está puesto feo el home.", Toast.LENGTH_LONG).show()
                    booksVM.changeDispalyMenu(false)
                }) {
                    Text(text = "Search by book gender")
                }
                DropdownMenuItem(onClick = {
                    Toast.makeText(context, "Aquí pondría una ayuda para utilizar la app.", Toast.LENGTH_LONG).show()
                    booksVM.changeDispalyMenu(false)
                }) {
                    Text(text = "Help")
                }
            }
        }
    )
}

@Composable
fun MyBottomBar(navController: NavController, bottomNavItems: List<BottomNavigationScreens>) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = MaterialTheme.colorScheme.background
                    )
                },
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
    val serchText by when (booksVM.actualScreen) {
        "favoriteScreen" -> booksVM.searchTextFav.observeAsState("")
        "homeScreen" -> booksVM.searchTextHome.observeAsState("")
        else -> booksVM.searchText.observeAsState("")
    }
    SearchBar(
        query = serchText,
        onQueryChange = {
            println("ACTUAL SCREEN IN SCAFOLD: " + booksVM.actualScreen)
            booksVM.searchDependingOnTheScreen(it)
        },
        onSearch = { booksVM.searchDependingOnTheScreen(it) },
        active = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close search",
                modifier = Modifier.clickable { booksVM.changeIsSearching(false) })
        },
        placeholder = { Text("What are you looking for?") },
        onActiveChange = {},
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth(),
        colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.primary)
    ) {}
}











