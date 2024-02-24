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
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.navigation.Routes
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun DetailScreen(navController: NavController, booksVM: BocksViewModel, previusScreen: String?) {
    booksVM.getBook(booksVM.bookGender, booksVM.idBook)
    val b: BookDetail by booksVM.book.observeAsState(BookDetail("", "","","","", "", "", "", "", "", "", ""))
    val isFavorite: Boolean by booksVM.isFavorite.observeAsState(false)
    booksVM.getFavorites()
    MyScaffold(navController = navController, book = b, booksVM = booksVM, favorite = isFavorite, previusScreen = previusScreen)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun book (b: BookDetail) {
    val textWith: Int = 200
    val textMargin: Int = 20
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
fun MyScaffold(navController: NavController, book: BookDetail, booksVM: BocksViewModel, favorite: Boolean, previusScreen: String?) {
    Scaffold (topBar = {MyTopAppBarDetail(navController = navController, booksVM = booksVM, b = book, favorite = favorite, previusScreen = previusScreen)}) { paddingValues ->
        book(book)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarDetail(navController: NavController, booksVM: BocksViewModel, b: BookDetail, favorite: Boolean, previusScreen: String?) {
    booksVM.isFavorite(b)
    val title = booksVM.bookGender
        /*if (booksVM.query.length == 15) booksVM.query.subSequence(7, booksVM.query.length-1)
        else "history"*/
    val root = when(previusScreen) {
        "listScreen" -> Routes.ListScreen.route
        else -> Routes.FavoriteScreen.route
    }
    TopAppBar(
        title = { Text(text = "$title books" ) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(
                    root
                ) }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.background)
            }
        },
        actions = {
            IconButton(onClick = { /*toDo*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
            }
            IconButton(onClick = {
                if (!favorite)
                    booksVM.saveFavorite(b)
                else
                    booksVM.deleteFavorite(b)
            }) {
                Icon(
                    imageVector =
                if (!favorite)
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