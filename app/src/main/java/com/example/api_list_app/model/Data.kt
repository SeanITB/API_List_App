package com.example.api_list_app.model

data class Data(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)