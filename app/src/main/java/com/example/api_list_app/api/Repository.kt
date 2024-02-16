package com.example.api_list_app.api

class Repository {

    val apiInterface = APIInterface.create()

    suspend fun getGender(/*query: String*/) = apiInterface.getGenderBook(/*query*/)

    suspend fun getOneBook(/*gender: String, id: String,*/) = apiInterface.getBook(/*gender, id,*/)
}