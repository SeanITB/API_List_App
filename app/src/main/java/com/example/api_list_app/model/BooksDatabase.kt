package com.example.api_list_app.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Book::class/*, ToRead::class, Reading::class, Read::class*/), version = 1)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}