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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
    booksVM.getBooks(booksVM.bookGender)
    booksVM.changeActualScreen("homeScreen")
    booksVM.changeTitele("Home")
    val showLoding: Boolean by booksVM.loadingApi.observeAsState(true)
    //val title = "Home"

    if (showLoding) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
    else {
        MyScaffoldHome(navController, booksVM)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffoldHome(navController: NavController, booksVM: BocksViewModel) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )
    val books: Data by booksVM.books.observeAsState(Data(emptyList(), "", 0))
    //val body = bodyHomeScreen(navController = navController, booksVM = booksVM, actualScreen = actualScreen)
    Scaffold (
        topBar = {MyTopAppBarList(navController = navController, booksVM = booksVM)},
        bottomBar = { MyBottomBar(navController = navController, bottomNavItems = bottomNavigationItems)}
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            Text(text = "Welcom to the best Libery App", fontWeight = FontWeight.Bold/*, fontSize = 40.dp*/)
            Spacer(modifier = Modifier.height(20.dp))
            SerchGenger(navController, booksVM)
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(books.books) { book ->
                            BookItem(navController, book, booksVM)
                        }
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerchGenger(navController: NavController, booksVM: BocksViewModel) {
    val genders = arrayOf("Computer Science", "Science Mathematics", "Economics Finance", "Business Management", "Politics Government", "History", "Philosophy", "Kotlin", "Android")
    booksVM.changeActualScreen("homeSreen")
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Book\nGender", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(16.dp))
        Box(
            //contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth(0.75f)
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
                genders.forEach { gender ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = gender, color = MaterialTheme.colorScheme.background) },
                        onClick = {
                            booksVM.changeExpanded(false)
                            booksVM.changeGender(gender)
                        }
                    )
                }
            }
        }
        Button(
            onClick = {
                navController.navigate(Routes.ListScreen.route)
            }
        ) {
            Icon(imageVector = Icons.Filled.GolfCourse, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
        }
    }
}