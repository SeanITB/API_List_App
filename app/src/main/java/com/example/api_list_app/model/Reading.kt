package com.example.api_list_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Reading(
    val authors: String,
    @PrimaryKey
    val id: String,
    val image: String,
    val subtitle: String,
    val title: String,
)