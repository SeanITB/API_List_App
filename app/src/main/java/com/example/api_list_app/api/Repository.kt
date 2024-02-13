package com.example.api_list_app.api

class Repository {

    val apiInterface = APIInterface.create()

    suspend fun getGender(gender: String) = apiInterface.getGenderBook(gender)

    suspend fun getOneBook(gender: String, id: String) = apiInterface.getBook(gender, id)
}