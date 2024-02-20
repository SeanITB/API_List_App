package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.BooksDatabase
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun DetailScreen(navController: NavController, booksVM: BocksViewModel/*, gender: String, book: String*/) {
    booksVM.getBook(booksVM.query, booksVM.idBook)
    val b: BookDetail by booksVM.book.observeAsState(BookDetail("", "","","","", "", "", "", "", "", "", ""))
    val isFavorite: MutableLiveData<MutableList<BookDetail>>  by booksVM.favorites.observeAsState(false) //toDo: aqui me he queado... El error de q no canvia favorito creo q es pq estava mirando el isFavorito y tendira q ser favorito
    booksVM.getFavorites()
    MyScaffold(navController = navController, book = b, booksVM = booksVM, favorite = isFavorite)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun book (b: BookDetail) {
    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val (image, titele, author, url) = createRefs()
        GlideImage(
            model = b.image,
            contentDescription = b.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 100.dp)
                    bottom.linkTo(url.top)
                    start.linkTo(parent.start)
                    end.linkTo(titele.start)
                }
        )
        Text(
            text = b.title,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(titele) {
                top.linkTo(image.top)
                start.linkTo(image.end)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = b.authors,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(author) {
                top.linkTo(titele.bottom)
                start.linkTo(image.end)
                end.linkTo(parent.end)
            }
        )
        //HyperlinkInSentenceExample(b)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController, book: BookDetail, booksVM: BocksViewModel, favorite: Boolean) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )

    Scaffold (topBar = {MyTopAppBarDetail(navController = navController, booksVM = booksVM, b = book, favorite = favorite)}) { paddingValues ->
        book(book)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarDetail(navController: NavController, booksVM: BocksViewModel, b: BookDetail, favorite: Boolean) {

    val title =
        if (booksVM.query.length == 15) booksVM.query.subSequence(7, booksVM.query.length-1)
        else "history"
    TopAppBar(
        title = { Text(text = "$title books" ) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = { navController.navigate(Routes.ListScreen.route) }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.background)
            }
        },
        actions = {
            IconButton(onClick = { /*toDo*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
            }
            IconButton(onClick = {
                if (favorite)
                    booksVM.saveFavorite(b)
                else
                    booksVM.deleteFavorite(b)
            }) {
                Icon(
                    imageVector =
                if (favorite)
                    Icons.Filled.FavoriteBorder
                else
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite", tint = MaterialTheme.colorScheme.background)
            }
        }
    )
}

@Composable
fun HyperlinkInSentenceExample(b: BookDetail) {
    val sourceText = "Check out my website: "
    val hyperlinkText = "CodingWithRashid"
    val endText = " for more awesome content."
    val uri = b.url

    val annotatedString = buildAnnotatedString {
        append(sourceText)
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Blue)) {
            append(hyperlinkText)
            addStringAnnotation(
                tag = "URL",
                annotation = uri,
                start = length - hyperlinkText.length,
                end = length
            )
        }
        append(endText)
    }
    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )

}