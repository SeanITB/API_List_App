package com.example.api_list_app.model

import android.app.Application
import androidx.room.Room

class BooksAplication: Application() {
    companion object {
        lateinit var database: BooksDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            BooksDatabase::class.java,
            "BooksDatabase"
        ).build()
    }
}