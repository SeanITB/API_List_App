package com.example.api_list_app.model

data class Data(
    val last_modified: String,
    val num_results: Int,
    val results: Results,
    val status: String
)