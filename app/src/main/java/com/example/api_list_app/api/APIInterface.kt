package com.example.api_list_app.api

import com.example.api_list_app.model.Data
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface APIInterface {

    @GET("lists/current/hardcover-fiction.json?api-key=FRCOKGStV1RVJeCSE8VOJVfQAjf7rw6l")
    suspend fun getCharacters(): Response<Data>

    companion object {
        val BASE_URL = "https://api.nytimes.com/svc/books/v3/"
        fun create(): APIInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(APIInterface::class.java)
        }
    }

}