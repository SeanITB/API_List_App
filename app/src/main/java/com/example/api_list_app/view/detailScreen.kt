package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun DetailScreen(navController: NavController, booksVM: BocksViewModel, previusScreen: String?) {
    //println("im in")
    booksVM.getBook(/*booksVM.bookGender,*/ booksVM.idBook)
    booksVM.getFavorites()
    //println("fav: ")
    val b: BookDetail by booksVM.book.observeAsState(BookDetail("", "","","","", "", "", "", "", "", "", ""))
    //println("THE TITELE : "+b.title)
    //println("size: ")
    //println("info")
    Text(text = "im hear")
    MyScaffold(navController = navController, book = b, booksVM = booksVM)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun book (b: BookDetail) {
    val textWith: Int = 200
    val textMargin: Int = 20
    println("THE BOOK TITELE: "+b.title)
    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val (image, titele, subtitele, author, description, bookInfo) = createRefs()
        GlideImage(
            model = b.image,
            contentDescription = b.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(150.dp)
                .height(250.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 100.dp)
                    //bottom.linkTo(description.top)
                    start.linkTo(parent.start)
                    end.linkTo(titele.start)
                }
        )
        Text(
            text = b.title,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            //maxLines = 2,
            modifier = Modifier
                .width(textWith.dp)
                .constrainAs(titele) {
                    top.linkTo(image.top)
                    //bottom.linkTo(subtitele.top)
                    start.linkTo(image.end, margin = 5.dp)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = b.subtitle,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(textWith.dp)
                .constrainAs(subtitele) {
                    top.linkTo(titele.bottom)
                    //bottom.linkTo(author.top)
                    start.linkTo(titele.start)
                    //end.linkTo(parent.end)
                }
        )
        Text(
            text = b.authors,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(textWith.dp)
                .constrainAs(author) {
                    top.linkTo(subtitele.bottom, margin = textMargin.dp)
                    //bottom.linkTo(bookInfo.top)
                    start.linkTo(titele.start)
                    //end.linkTo(parent.end)
                }
        )
        Text(
            text = """
                Publisher: ${b.publisher}
                Subject: toDo
                Language: Englesh
                Pages: ${b.pages}
                Year: ${b.year}
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(textWith.dp)
                .constrainAs(bookInfo) {
                    top.linkTo(author.bottom, margin = textMargin.dp)
                    //bottom.linkTo(description.top)
                    start.linkTo(titele.start)
                    //end.linkTo(parent.end)
                }
        )
        Text(
            text = b.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(400.dp)
                .constrainAs(description) {
                    top.linkTo(bookInfo.bottom, margin = textMargin.dp)
                    //bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        //HyperlinkInSentenceExample(b)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController, book: BookDetail, booksVM: BocksViewModel) {
    //println("el TITELE...: ${book.title}")
    //println("el ID: ${book.id}")
    //println("el id pero de booksVM: "+booksVM.idBook)
    //val b: Book = booksVM.getBookById(booksVM.idBook) //toDO: esta petando los favoritos
    //println("Ya esta con el fav")
    //println("now im hear")
    Scaffold (topBar = {MyTopAppBarDetail(navController = navController, booksVM = booksVM, b = book, )}) { paddingValues ->
        book(book)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarDetail(navController: NavController, booksVM: BocksViewModel, b: BookDetail) {
    //println("top bar")
    val book: Book = booksVM.getBookById(booksVM.idBook) //toDO: esta petando los favoritos
    booksVM.isFavorite(book)
    //println("get fav")
    val isFavorite: Boolean by booksVM.isFavorite.observeAsState(false)
    val isToRead: Boolean by booksVM.isTR.observeAsState(false)
    val favorites: List<Book> by booksVM.favorites.observeAsState(emptyList())
    println("favorites that i have: "+favorites.size)
    println("state of favorite: "+isFavorite)
    //println("get booleans")
    //val title = booksVM.bookGender

    TopAppBar(
        title = { Text(text = "${booksVM.title} books" ) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            /*
            IconButton(onClick = {
                navController.navigate(
                    booksVM.getRout()
                ) }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.background)
            }

             */
        },
        actions = {

            /*IconButton(onClick = { if (isToRead == true){

                booksVM.saveAsToRead(book)
            } else booksVM.deleteToRead(book) }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
            }*/
            IconButton(onClick = {
                if (isFavorite == false){
                    println("save as favorite DETAIL")
                    println("publisher DETAIL: "+b.publisher)
                    booksVM.saveFavorite(book)
                    println("saved DETAIL")
                    println("favorites that i have DETAIL: "+favorites.size)
                } else {
                    println("delete from fav")
                    booksVM.deleteFavorite(book)
                    println("favorites that i have: "+favorites.size)
                } }) {
                Icon(imageVector = if (isFavorite == true) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite, contentDescription = "Favorite", tint = MaterialTheme.colorScheme.background)
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