package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.Data
import com.example.api_list_app.model.ToRead
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun LiberiScreen(navController: NavController, booksVM: BocksViewModel) {
    val showLoding: Boolean by booksVM.loadingTR.observeAsState(true)
    booksVM.changeActualScreen("liberiScreen")
    booksVM.changeTitele("Library")
    booksVM.getToRead()
    if (showLoding) {
        ConstraintLayout {
            val (circularProgresBar, text) = createRefs()
            Box(
                modifier = Modifier
                    //.width(400.dp)
                    //.height(400.dp)
                    .constrainAs(circularProgresBar) {
                        top.linkTo(parent.top)
                        bottom.linkTo(text.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                "Waiting for a response...",
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(circularProgresBar.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    } else {
        MyScaffoldLibrary(navController = navController, booksVM = booksVM)
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffoldLibrary(navController: NavController, booksVM: BocksViewModel) {
    val toReadBooks: List<ToRead> by booksVM.bookstoRead.observeAsState(emptyList())
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
    ) {  paddingValues ->
        if (toReadBooks.isNotEmpty()) {
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
                                toReadBooks
                            ) { book ->
                                BookItemToRead(navController, book, booksVM)
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItemToRead(navController: NavController, book: ToRead, booksVM: BocksViewModel) {
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
                .clickable {
                    booksVM.changeInitialScreen(booksVM.previusScreen)
                    booksVM.changePreviusScreen(booksVM.actualScreen)
                    booksVM.changeIdBook(book.id)
                    navController.navigate(Routes.DetailScreen.route)
                }
        ) {
            GlideImage(
                model = book.image,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(textMarginHeight.dp))
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(textMarginHeight.dp))
            Text(
                text = book.authors,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
