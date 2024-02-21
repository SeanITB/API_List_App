package com.example.api_list_app.view

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun FavoriteScreen(navController: NavController, booksVM: BocksViewModel) {
    booksVM.getFavorites()
    val actualScreen = "favoriteScreen"
    val showLoding: Boolean by booksVM.loadingDB.observeAsState(true)
    val favorites: MutableList<BookDetail> by booksVM.favorites.observeAsState(mutableListOf())
    if (showLoding) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        MyScaffold(navController, favorites, booksVM, actualScreen)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItem(navController: NavController, generalBook: Book,  favoriteBook: BookDetail, booksVM: BocksViewModel, actualScreen: String?) {
    val bookk: MutableList<Any> = mutableListOf() // toDo: estoy intentando hacer solo una funcion de BookItem
    bookk.add( when (actualScreen) {
        "listSreen" -> generalBook
        else -> favoriteBook
        }
    )
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable(/*enabled = false*/) {
                    booksVM.changeIdBook(bookk[0].id)
                    booksVM.setgender("book/")
                    navController.navigate(Routes.DetailScreen.createRouteToDetail(actualScreen))
                }
        ) {
            GlideImage(
                model = book.image,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController, bf: MutableList<BookDetail>, booksVM: BocksViewModel, actualScreen: String?) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )
    val title = "Favorite"

    Scaffold (
        topBar = {MyTopAppBarList(navController = navController, booksVM = booksVM, title = title)},
        bottomBar = { MyBottomBar(navController = navController, bottomNavItems = bottomNavigationItems)}
    ) { paddingValues ->
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(bf) { book ->
                BookItem(navController, book, booksVM, actualScreen)
            }
        }
    }
}
