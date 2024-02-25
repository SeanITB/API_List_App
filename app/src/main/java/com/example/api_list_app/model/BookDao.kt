package com.example.api_list_app.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {

    //DML favorits table
    @Query("SELECT * FROM BookDetail")
    suspend fun getAllBooks(): MutableList<BookDetail>
    @Query("SELECT * FROM BookDetail WHERE id =:bookId")
    suspend fun getBookById(bookId: String): MutableList<BookDetail>
    @Insert
    suspend fun addBook(book: BookDetail)
    @Delete
    suspend fun deleteBook(book: BookDetail)

    //DML toRead table
    @Query("SELECT * FROM BookDetail")
    suspend fun getAllBooksToRead(): MutableList<BookDetail>
    @Query("SELECT * FROM BookDetail WHERE id =:bookId")
    suspend fun getBookByIdToRead(bookId: String): MutableList<BookDetail>
    @Insert
    suspend fun addBookToRead(book: BookDetail)
    @Delete
    suspend fun deleteBookToRead(book: BookDetail)

    //DML reading table
    @Query("SELECT * FROM BookDetail")
    suspend fun getAllBooksReading(): MutableList<BookDetail>
    @Query("SELECT * FROM BookDetail WHERE id =:bookId")
    suspend fun getBookByIdReading(bookId: String): MutableList<BookDetail>
    @Insert
    suspend fun addBookReading(book: BookDetail)
    @Delete
    suspend fun deleteBookReading(book: BookDetail)

    //DML read table
    @Query("SELECT * FROM BookDetail")
    suspend fun getAllBooksRead(): MutableList<BookDetail>
    @Query("SELECT * FROM BookDetail WHERE id =:bookId")
    suspend fun getBookByIdRead(bookId: String): MutableList<BookDetail>
    @Insert
    suspend fun addBookRead(book: BookDetail)
    @Delete
    suspend fun deleteBookRead(book: BookDetail)

}