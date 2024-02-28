package com.example.api_list_app.api

import com.example.api_list_app.model.Book
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
    suspend fun saveAsFavorite(book: Book) = daoInterfase.addBook(book)
    suspend fun deleteFavorite(b: Book) = daoInterfase.deleteBook(b)
    suspend fun isFavorite(b: Book) = daoInterfase.getBookById(b.id).isNotEmpty()
    suspend fun getFavorites() = daoInterfase.getAllBooks()

    //table toRead
    suspend fun saveAsToRead(book: Book) = daoInterfase.addBookToRead(book)
    suspend fun deleteToRead(b: Book) = daoInterfase.deleteBookToRead(b)
    suspend fun isToRead(b: Book) = daoInterfase.getBookByIdToRead(b.id).isNotEmpty()
    suspend fun getToRead() = daoInterfase.getAllBooksToRead()

    //table reading
    suspend fun saveAsReading(book: Book) = daoInterfase.addBookReading(book)
    suspend fun deleteReading(b: Book) = daoInterfase.deleteBookReading(b)
    suspend fun isReading(b: Book) = daoInterfase.getBookByIdReading(b.id).isNotEmpty()
    suspend fun getReading() = daoInterfase.getAllBooksReading()

    //table read
    suspend fun saveAsRead(book: Book) = daoInterfase.addBookRead(book)
    suspend fun deleteRead(b: Book) = daoInterfase.deleteBookRead(b)
    suspend fun isRead(b: Book) = daoInterfase.getBookByIdRead(b.id).isNotEmpty()
    suspend fun getRead() = daoInterfase.getAllBooksRead()
}