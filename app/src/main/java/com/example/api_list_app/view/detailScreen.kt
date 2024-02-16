package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.Bookk
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun DetailScreen(navController: NavController, booksVM: BocksViewModel/*, gender: String, book: String*/) {
    booksVM.getBook(booksVM.query, booksVM.idBook)
    val b: Bookk by booksVM.book.observeAsState(Bookk("", "","","","", "", "", "", "", "", "", ""))
    MyScaffold(navController = navController, book = b, booksVM = booksVM)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun book (b: Bookk) {
    Column {
        Text(text = "hola no")
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                model = b.image,
                contentDescription = b.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
            Text(
                text = "${b.title} nooo",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController, b: Bookk, booksVM: BocksViewModel) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )

    Scaffold (topBar = {MyTopAppBar(navController = navController, booksVM = booksVM)}, bottomBar = { MyBottomBar(navController, bottomNavigationItems)}) { paddingValues ->
        book(b)
    }
}