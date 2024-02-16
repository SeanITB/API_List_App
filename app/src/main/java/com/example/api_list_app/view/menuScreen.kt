package com.example.api_list_app.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.api_list_app.model.Book
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel
/*
@Composable
fun MenuScreen(navController: NavController, booksVM: BocksViewModel) {
    //booksVM.getBook()
    val b: Book by booksVM.book.observeAsState(Book("", "", "", "", "", "", "", ""))
    Column {
        Text(text = "hola q tal")
    }
}

 */