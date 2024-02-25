package com.example.api_list_app.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(BookDetail::class, BookDetail::class, BookDetail::class), version = 1)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}