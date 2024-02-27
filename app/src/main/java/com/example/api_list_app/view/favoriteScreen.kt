package com.example.api_list_app.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun FavoriteScreen(navController: NavController, booksVM: BocksViewModel) {
    booksVM.getFavorites()
    val showLoding: Boolean by booksVM.loadingDB.observeAsState(true)
    //val isSearch: Boolean by booksVM.isSearching.observeAsState(false)
    val actualScreen = /*if (isSearch) "search" else */"favoriteScreen"
    val title = "Favorite"
    if (showLoding) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        MyScaffold(navController, booksVM, actualScreen, title)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItem(navController: NavController, favoriteBook: BookDetail, booksVM: BocksViewModel, actualScreen: String?) {
    val textMarginHeight : Int = 10
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
                    booksVM.changeIdBook(favoriteBook.id)
                    booksVM.changeGender("book/")
                    navController.navigate(Routes.DetailScreen.createRouteToDetail(actualScreen))
                }
        ) {
            GlideImage(
                model = favoriteBook.image,
                contentDescription = favoriteBook.id,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(textMarginHeight.dp))
            Text(
                text = favoriteBook.title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(textMarginHeight.dp))
            Text(
                text = favoriteBook.authors,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

