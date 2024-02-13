package com.example.api_list_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api_list_app.api.Repository
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BocksViewModel: ViewModel() {

    private val repository = Repository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _books = MutableLiveData<Data>()
    val books = _books
    private val _book = MutableLiveData<Book>()
    val book = _book

    var gender by mutableStateOf("search/history/")
        private set

    var idBook by mutableStateOf("3319546813")
        private set

    fun setgender(value: String) {
        this.gender = value
    }

    fun changeIdBook(value: String) {
        this.idBook = value
    }

    fun getBooks(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getGender(gender)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    _books.value = response.body()
                    _loading.value = false
                }
                else {
                    Log.e("ERROR : ", response.message())
                }
            }
        }
    }

    fun getBook(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOneBook(gender, idBook)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    _books.value = response.body()
                    _loading.value = false
                }
                else {
                    Log.e("ERROR : ", response.message())
                }
            }
        }
    }
}