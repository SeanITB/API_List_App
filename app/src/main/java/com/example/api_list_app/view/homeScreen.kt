package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel


@Composable
fun HomeScreen(navController: NavController, booksVM: BocksViewModel) {
    //booksVM.getBooksByGender(booksVM.bookGender)
    booksVM.getBooksRecent()
    booksVM.changeActualScreen("homeScreen")
    booksVM.changeTitele("Home")
    println("size form fount HOME: "+ booksVM.booksByGenderRecent.value?.books?.size)
    val showLoding: Boolean by booksVM.loadingApi.observeAsState(true)
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
            androidx.compose.material3.Text(
                "Waiting for a response...",
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(circularProgresBar.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
    else {
        MyScaffoldHome(navController, booksVM)
    }
}

//toDo: ¡¡¡ATENCIÓN!!!, composable kilométrico,
//Esto es porque si lo desgloso en funciones después no puedo utilizar el constreynLoyaut

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffoldHome(navController: NavController, booksVM: BocksViewModel) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )
    val booksRecent: Data by booksVM.booksByGenderRecent.observeAsState(Data(emptyList(), "", 0))
    val isSearch: Boolean by booksVM.isSearching.observeAsState(false)
    Scaffold (
        topBar = {
            MyTopAppBarList(navController = navController, booksVM = booksVM)
            if (isSearch) MySearchBar(navController, booksVM)
                 },
        bottomBar = { MyBottomBar(navController = navController, bottomNavItems = bottomNavigationItems)}
    ) { paddingValues ->
        ConstraintLayout{
            val (searchGenderCL, booksCL, booksH) = createRefs()
            //Text(text = "Welcom to the best Libery App", fontWeight = FontWeight.Bold/*, fontSize = 40.dp*/)
            //Spacer(modifier = Modifier.height(20.dp))
            //Spacer(modifier = Modifier.height(20.dp))
            //Spacer(modifier = Modifier.height(200.dp))
            //SerchGenger(navController, booksVM)
            val genders = arrayOf("Computer Science", "Science Mathematics", "Economics Finance", "Business Management", "Politics Government", "History", "Philosophy", "Kotlin", "Android")
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .constrainAs(searchGenderCL) {
                        top.linkTo(parent.top, margin = 75.dp)
                        bottom.linkTo(booksCL.top, margin = 20.dp)
                    }
            ) {
                Spacer(modifier = Modifier.padding(16.dp))
                //Text(text = "Book\nGender", fontWeight = FontWeight.Bold)
                //Spacer(modifier = Modifier.padding(16.dp))
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
                        booksVM.changePreviusScreen(booksVM.actualScreen)
                        navController.navigate(Routes.ListScreen.route)
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .height(65.dp)
                        .width(65.dp)
                        .padding(8.dp),
                ) {
                    Text(text = "GO")
                    //Icon(imageVector = Icons.Filled.GolfCourse, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
                }
            }
            Box(modifier = Modifier
                //.fillMaxWidth(0.8f)
                //.fillMaxHeight(0.8f)
                .height(525.dp)
                .width(400.dp)
                .constrainAs(booksCL) {
                    top.linkTo(searchGenderCL.top, margin = 50.dp)
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
                        items(booksRecent.books) { book ->
                            BookItem(navController, book, booksVM)
                        }
                    }
                )
            }
            /* toDo: whant to do a drabagle list
            Box(modifier = Modifier
                //.fillMaxWidth(0.8f)
                //.fillMaxHeight(0.8f)
                .height(525.dp)
                .width(400.dp)
                .constrainAs(booksH) {
                    top.linkTo(booksCL.top, margin = 50.dp)
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
                        items(booksRecent.books) { book ->
                            BookItem(navController, book, booksVM)
                        }
                    }
                )
            }

             */
        }
    }
}

// toDo: ¡¡¡ATETNCION!!!, aqui estari el apartado de searchGender si no tuviera el error del constraynLoyaut
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerchGenger(navController: NavController, booksVM: BocksViewModel) {
    val genders = arrayOf("Computer Science", "Science Mathematics", "Economics Finance", "Business Management", "Politics Government", "History", "Philosophy", "Kotlin", "Android")
    booksVM.changeActualScreen("homeSreen")
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        //Text(text = "Book\nGender", fontWeight = FontWeight.Bold)
        //Spacer(modifier = Modifier.padding(16.dp))
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
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .height(65.dp)
                .width(65.dp)
                .padding(8.dp),
        ) {
            Text(text = "GO")
            //Icon(imageVector = Icons.Filled.GolfCourse, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
        }
    }
}*/

