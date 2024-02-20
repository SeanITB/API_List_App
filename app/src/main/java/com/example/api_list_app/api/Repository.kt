package com.example.api_list_app.api

import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.BooksAplication

class Repository {

    var apiInterface = APIInterface.create()

    var daoInterfase = BooksAplication.database.bookDao()

    //Api conection
    suspend fun getGender(/*query: String*/) = apiInterface.getGenderBook(/*query*/)

    suspend fun getOneBook(/*gender: String,*/ id: String) = apiInterface.getBook(/*gender,*/ id)

    //Database functions
    suspend fun saveAsFavorite(book: BookDetail) = daoInterfase.addBook(book)
    suspend fun deleteFavorite(b: BookDetail) = daoInterfase.deleteBook(b)
    suspend fun isFavorite(b: BookDetail) = daoInterfase.getBookById(b.id.toInt()).isNotEmpty()
    suspend fun getFavorites() = daoInterfase.getAllBooks()
}