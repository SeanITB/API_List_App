package com.example.api_list_app.model

data class Data(
    val key: String,
    val name: String,
    val subject_type: String,
    val work_count: Int,
    val works: List<Work>
)