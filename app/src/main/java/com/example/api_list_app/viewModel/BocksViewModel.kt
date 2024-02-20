package com.example.api_list_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api_list_app.api.Repository
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BocksViewModel: ViewModel() {
    private var repository = Repository()

    //Api data
    private val _loadingApi = MutableLiveData(true)
    val loadingApi = _loadingApi
    private val _books = MutableLiveData<Data>()
    val books = _books
    private val _book = MutableLiveData<BookDetail>()
    val book = _book

    //Database data
    private val _loadingDB = MutableLiveData(true)
    val loadingDB = _loadingDB
    private val _isFavorite = MutableLiveData(false)
    val isFavorite = _isFavorite
    private val _favorites = MutableLiveData<MutableList<BookDetail>>()
    val favorites = _favorites

    var query by mutableStateOf("search/history/")
        private set


    //var query = "search/history"

    var idBook by mutableStateOf("3319546813")
        private set

    fun setgender(value: String) {
        this.query = value
    }



    fun changeIdBook(value: String) {
        this.idBook = value
    }

    fun getBooks(/*query: String*/){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getGender(/*query*/)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    _books.value = response.body()
                    _loadingApi.value = false
                }
                else {
                    Log.e("ERROR : ", response.message())
                }
            }
        }
    }


    fun getBook(gender: String, id: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOneBook(/*gender,*/ id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    _book.value = response.body()
                    _loadingApi.value = false
                }
                else {
                    Log.e("ERROR : ", response.message())
                }
            }
        }
    }

    fun getFavorites(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFavorites()
            withContext(Dispatchers.Main) {
                _favorites.value = response
                _loadingDB.value = false
            }
        }
    }

    fun isFavorite(bookDetail: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isFavorite(bookDetail)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveFavorite(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsFavorite(b)
        }
    }

    fun deleteFavorite(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavorite(b)
        }
    }
}