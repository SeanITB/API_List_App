package com.example.api_list_app.view

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun LiberiScreen(navController: NavController, booksVM: BocksViewModel) {
    val showLoding: Boolean by booksVM.loadingTR.observeAsState(true)
    booksVM.changeActualScreen("liberiScreen")
    booksVM.changeTitele("LIBERI")
    //booksVM.getToRead()
    if (showLoding) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        MyScaffold(navController = navController, booksVM = booksVM)
    }
    

}