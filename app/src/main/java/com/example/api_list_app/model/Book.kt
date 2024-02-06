package com.example.api_list_app.model

data class Book(
    val author: String,
    val book_image: String,
    val book_image_height: Int,
    val book_image_width: Int,
    val book_review_link: String,
    val buy_links: BuyLink,
    val description: String,
    val price: String,
    val publisher: String,
    val title: String,
)