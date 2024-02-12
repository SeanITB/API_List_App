package com.example.api_list_app.model

data class Data(
    val books: List<Book>,
    val status: String,
    val total: Int
)