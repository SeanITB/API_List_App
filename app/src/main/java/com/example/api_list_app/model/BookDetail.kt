package com.example.api_list_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class BookDetail(
    val authors: String,
    val description: String,
    val download: String,
    @PrimaryKey
    val id: String,
    val image: String,
    val pages: String,
    @ColumnInfo(defaultValue = "")
    val publisher: String,
    val status: String,
    val subtitle: String,
    val title: String,
    val url: String,
    val year: String
)