package com.example.api_list_app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MyRecyclerBooksView(){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(get)
    }
}
