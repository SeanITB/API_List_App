package com.example.api_list_app.model

import androidx.annotation.DrawableRes

data class Book(
    val author: String,
    @DrawableRes val book_image: Int,
    val book_image_height: Int,
    val book_image_width: Int,
    val book_review_link: String,
    val buy_links: BuyLink,
    val description: String,
    val price: String,
    val publisher: String,
    val title: String,
)