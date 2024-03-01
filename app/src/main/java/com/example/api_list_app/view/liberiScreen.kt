package com.example.api_list_app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun LiberiScreen(navController: NavController, booksVM: BocksViewModel) {
    val showLoding: Boolean by booksVM.loadingTR.observeAsState(true)
    booksVM.changeActualScreen("liberiScreen")
    booksVM.changeTitele("LIBERI")
    //booksVM.getToRead()
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
        MyScaffold(navController = navController, booksVM = booksVM)
    }


}