package com.example.api_list_app.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM BookDetail")
    suspend fun getAllBooks(): MutableList<BookDetail>
    @Query("SELECT * FROM BookDetail WHERE id =:bookId")
    suspend fun getBookById(bookId: Int): MutableList<BookDetail>
    @Insert
    suspend fun addBook(book: BookDetail)
    @Delete
    suspend fun deleteBook(book: BookDetail)
}