package com.example.api_list_app.api

class Repository {

    val apiInterface = APIInterface.create()

    suspend fun getAllCharacters() = apiInterface.getCharacters()
}