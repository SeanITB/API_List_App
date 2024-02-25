package com.example.api_list_app.api

import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.BooksAplication

class Repository {

    var apiInterface = APIInterface.create()

    var daoInterfase = BooksAplication.database.bookDao()

    //Api conection
    suspend fun getGender(gender: String) = apiInterface.getGenderBook(gender)

    suspend fun getOneBook(/*gender: String,*/ id: String) = apiInterface.getBook(/*gender,*/ id)

    //Database functions
    //table favorites
    suspend fun saveAsFavorite(book: BookDetail) = daoInterfase.addBook(book)
    suspend fun deleteFavorite(b: BookDetail) = daoInterfase.deleteBook(b)
    suspend fun isFavorite(b: BookDetail) = daoInterfase.getBookById(b.id).isNotEmpty()
    suspend fun getFavorites() = daoInterfase.getAllBooks()

    //table toRead
    suspend fun saveAsToRead(book: BookDetail) = daoInterfase.addBookToRead(book)
    suspend fun deleteToRead(b: BookDetail) = daoInterfase.deleteBookToRead(b)
    suspend fun isToRead(b: BookDetail) = daoInterfase.getBookByIdToRead(b.id).isNotEmpty()
    suspend fun getToRead() = daoInterfase.getAllBooksToRead()

    //table reading
    suspend fun saveAsReading(book: BookDetail) = daoInterfase.addBookReading(book)
    suspend fun deleteReading(b: BookDetail) = daoInterfase.deleteBookReading(b)
    suspend fun isReading(b: BookDetail) = daoInterfase.getBookByIdReading(b.id).isNotEmpty()
    suspend fun getReading() = daoInterfase.getAllBooksReading()

    //table read
    suspend fun saveAsRead(book: BookDetail) = daoInterfase.addBookRead(book)
    suspend fun deleteRead(b: BookDetail) = daoInterfase.deleteBookRead(b)
    suspend fun isRead(b: BookDetail) = daoInterfase.getBookByIdRead(b.id).isNotEmpty()
    suspend fun getRead() = daoInterfase.getAllBooksRead()
}