package com.example.api_list_app.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {

    //DML favorits table
    @Query("SELECT * FROM Book")
    suspend fun getAllBooks(): MutableList<Book>
    @Query("SELECT * FROM Book WHERE id =:bookId")
    suspend fun getBookById(bookId: String): MutableList<Book>
    @Insert
    suspend fun addBook(book: Book)
    @Delete
    suspend fun deleteBook(book: Book)

    //DML toRead table
    @Query("SELECT * FROM Book")
    suspend fun getAllBooksToRead(): MutableList<Book>
    @Query("SELECT * FROM Book WHERE id =:bookId")
    suspend fun getBookByIdToRead(bookId: String): MutableList<Book>
    @Insert
    suspend fun addBookToRead(book: Book)
    @Delete
    suspend fun deleteBookToRead(book: Book)

    //DML reading table
    @Query("SELECT * FROM Book")
    suspend fun getAllBooksReading(): MutableList<Book>
    @Query("SELECT * FROM Book WHERE id =:bookId")
    suspend fun getBookByIdReading(bookId: String): MutableList<Book>
    @Insert
    suspend fun addBookReading(book: Book)
    @Delete
    suspend fun deleteBookReading(book: Book)

    //DML read table
    @Query("SELECT * FROM Book")
    suspend fun getAllBooksRead(): MutableList<Book>
    @Query("SELECT * FROM Book WHERE id =:bookId")
    suspend fun getBookByIdRead(bookId: String): MutableList<Book>
    @Insert
    suspend fun addBookRead(book: Book)
    @Delete
    suspend fun deleteBookRead(book: Book)

}