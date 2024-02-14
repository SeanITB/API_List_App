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
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun MyRecyclerBooksView(navController: NavController, booksVM: BocksViewModel){
    booksVM.getBooks(/*"search/history"*/)
    val showLoding: Boolean by booksVM.loading.observeAsState(true)
    val books: Data by booksVM.books.observeAsState(Data(emptyList(), "", 0))
    if (showLoding) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
    else {
        MyScaffold(navController, books, booksVM)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController,  books: Data, booksVM: BocksViewModel) {
    Scaffold (bottomBar = {MyBottomBar(navController)}) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(books.books) { book ->
                BookItem(navController, book, booksVM)
            }
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItem(navController: NavController, book: Book, booksVM: BocksViewModel) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable(/*enabled = false*/){
                    booksVM.changeIdBook(book.id)
                    booksVM.setgender("book/")
                    navController.navigate(Routes.DetailScreen.route)
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

@Composable
fun MyBottomBar(navController: NavController) {
    val arrIcon = arrayOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Settings)
    val arrMsg = arrayOf("Home", "Favorite", "Settings")

    /* toDo: Se va directamente a detail screen
    val arrNavController = arrayOf(
        navController.navigate(Routes.MenuScreen.route),
        navController.navigate(Routes.FavoriteList.route),
        navController.navigate(Routes.SettingdSreen.route)
        )

     */


    BottomNavigation (backgroundColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.primary) {
        //arrIcon.indices.forEach { index ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text(text = "Home") },
                selected = true,
                onClick = {  }
            )
        //}
    }


}

