package com.example.api_list_app.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.Data
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun DetailScreen(navController: NavController, booksVM: BocksViewModel) {
    //val books: Data by booksVM.books.observeAsState(Data(emptyList(), "", 0))
    val b: Book by booksVM.book.observeAsState(Book("", "", "", "", "", ""))
    booksVM.getBook()
    Column {

    }
}