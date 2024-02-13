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
    val showLoding: Boolean by booksVM.loading.observeAsState(true)
    val books: Data by booksVM.books.observeAsState(Data(emptyList(), "", 0))
    booksVM.getBooks()
    if (showLoding) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
    else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(books.books) { book ->
                MyScaffold(navController, book, booksVM)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController,  book: Book, booksVM: BocksViewModel) {
    Scaffold (bottomBar = {MyBottomBar()}) {
        BookItem(navController = navController, book = book, booksVM = booksVM)
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
                .clickable {
                    booksVM.changeIdBook(book.id)
                    booksVM.setgender("book")
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
fun MyBottomBar() {
    BottomNavigation () {

    }
}

