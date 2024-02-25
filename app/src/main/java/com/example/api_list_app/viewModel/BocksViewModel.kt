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

    var expanded by mutableStateOf(false)
        private set

    var bookGender by mutableStateOf("recent")
        private set

    //var query by mutableStateOf("search/${this.bookGender}/")
      //  private set

    //var query = "search/history"

    var idBook by mutableStateOf("3319546813")
        private set


    fun changeGender(value: String) {
        this.bookGender = when(value) {
            "Computer Science" -> "computer+science"
            "Science Mathematics" ->"science+mathematics"
            "Economics Finance" -> "economics+finance"
            "Business Management" -> "business+management"
            "Politics Government" -> "politics+government"
            "History" -> "history"
            "Philosophy" -> "philosophy"
            "Kotlin" -> "kotlin"
            else -> "android"
        }
    }

    fun changeIdBook(value: String) {
        this.idBook = value
    }

    fun changeExpanded (value: Boolean) {
        this.expanded = value
    }

    fun getBooks(gender: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getGender(gender)
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
            _isFavorite.postValue(true)
        }
    }

    fun deleteFavorite(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavorite(b)
            _isFavorite.postValue(false)
        }
    }

    fun getToRead(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getToRead()
            withContext(Dispatchers.Main) {
                _favorites.value = response
                _loadingDB.value = false
            }
        }
    }

    fun isToRead(bookDetail: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isToRead(bookDetail)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveAsToRead(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsToRead(b)
            _isFavorite.postValue(true)
        }
    }

    fun deleteToRead(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteToRead(b)
            _isFavorite.postValue(false)
        }
    }

    fun getReading(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getReading()
            withContext(Dispatchers.Main) {
                _favorites.value = response
                _loadingDB.value = false
            }
        }
    }

    fun isReading(bookDetail: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isReading(bookDetail)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveAsReading(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsReading(b)
            _isFavorite.postValue(true)
        }
    }

    fun deleteReading(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteReading(b)
            _isFavorite.postValue(false)
        }
    }

    fun getRead(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getRead()
            withContext(Dispatchers.Main) {
                _favorites.value = response
                _loadingDB.value = false
            }
        }
    }

    fun isRead(bookDetail: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isRead(bookDetail)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveAsRead(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsRead(b)
            _isFavorite.postValue(true)
        }
    }

    fun deleteRead(b: BookDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteRead(b)
            _isFavorite.postValue(false)
        }
    }

}