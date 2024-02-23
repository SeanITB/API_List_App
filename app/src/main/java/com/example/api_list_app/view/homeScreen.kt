package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun HomeScreen(navController: NavController, booksVM: BocksViewModel) {
    booksVM.getBooks(/*"search/history"*/)
    val actualScreen: String = "homeScreen"
    val showLoding: Boolean by booksVM.loadingApi.observeAsState(true)
    val title = "Home"

    if (showLoding) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
    else {
        MyScaffoldHome(navController,  booksVM, actualScreen, title)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffoldHome(navController: NavController, booksVM: BocksViewModel, actualScreen: String, title: CharSequence) {
    val books: Data by booksVM.books.observeAsState(Data(emptyList(), "", 0))
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )

    Scaffold (
        topBar = {MyTopAppBarList(navController = navController, booksVM = booksVM, title = title)},
        bottomBar = { MyBottomBar(navController = navController, bottomNavItems = bottomNavigationItems)}
    ) { paddingValues ->
        Column {
            Text(text = "Welcom to the best Libery App")
            //SerchGenger(booksVM)
            LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp), content = {
                items(books.books) { book ->
                    BookItemHome(navController, book, booksVM, actualScreen)
                }
            })
        }
        //Spacer(modifier = Modifier.height(50.dp))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerchGenger(booksVM: BocksViewModel) {
    //toDo: me quede aqui
    val genders = arrayOf("recent", "history", "python", "kotlin", "java", "android", "computer+science", "science+mathematics", "economics+finance")
    Row {
        Text(text = """
                    PLAY 
                    MODE
                """.trimIndent(), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(16.dp))
        Box(
            //contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            OutlinedTextField(
                value = booksVM.bookGender,
                onValueChange = { booksVM.changeGender(booksVM.bookGender) },
                label = { Text(text = "GENDER BOOK", color = MaterialTheme.colorScheme.primary) },
                enabled = false,
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .clickable { booksVM.changeExpanded(true) }
                    .fillMaxWidth(0.6f)
            )
            DropdownMenu(
                expanded = booksVM.expanded,
                onDismissRequest = { booksVM.changeExpanded(false) },
                modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
            ) {
                genders.forEach { difficulty ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = difficulty, color = MaterialTheme.colorScheme.background) },
                        onClick = {
                            booksVM.changeExpanded(false)
                            booksVM.changeGender(difficulty)
                        }
                    )
                }
            }
        }
    }
}






@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItemHome(navController: NavController, book: Book, booksVM: BocksViewModel, actualSreen: String) {
    val textMarginHeight : Int = 10
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable(/*enabled = false*/) {
                    booksVM.changeIdBook(book.id)
                    booksVM.changeGender("book/")
                    navController.navigate(Routes.DetailScreen.createRouteToDetail(actualSreen))
                }
        ) {
            GlideImage(
                model = book.image,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(textMarginHeight.dp))
            androidx.compose.material3.Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(textMarginHeight.dp))
            androidx.compose.material3.Text(
                text = book.authors,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}