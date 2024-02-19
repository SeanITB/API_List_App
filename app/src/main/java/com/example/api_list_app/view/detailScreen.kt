package com.example.api_list_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.api_list_app.model.Bookk
import com.example.api_list_app.navigation.BottomNavigationScreens
import com.example.api_list_app.viewModel.BocksViewModel

@Composable
fun DetailScreen(navController: NavController, booksVM: BocksViewModel/*, gender: String, book: String*/) {
    booksVM.getBook(booksVM.query, booksVM.idBook)
    val b: Bookk by booksVM.book.observeAsState(Bookk("", "","","","", "", "", "", "", "", "", ""))
    MyScaffold(navController = navController, book = b, booksVM = booksVM)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun book (b: Bookk) {
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
        HyperlinkInSentenceExample(b)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(navController: NavController, book: Bookk, booksVM: BocksViewModel) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Favorite,
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Settings
    )

    Scaffold (topBar = {MyTopAppBarDetail(navController = navController, booksVM = booksVM)}) { paddingValues ->
        book(book)
    }
}

@Composable
fun HyperlinkInSentenceExample(b: Bookk) {
    val sourceText = "Check out my website: "
    val hyperlinkText = "CodingWithRashid"
    val endText = " for more awesome content."
    val uri =  b.url

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