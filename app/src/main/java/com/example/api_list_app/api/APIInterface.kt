package com.example.api_list_app.api

import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.Data
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface APIInterface {

    @GET("search/recent")
    suspend fun getRecentBook(): Response<Data>
    @GET("search/{gender}")
    suspend fun getGenderBook(@Path("gender") charQuery: String): Response<Data>

    @GET("book/{id}")
    suspend fun getBook(/*@Path("query") charQuery: String,*/@Path("id") charId: String): Response<BookDetail>

    companion object {
        val BASE_URL = "https://www.dbooks.org/api/"
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