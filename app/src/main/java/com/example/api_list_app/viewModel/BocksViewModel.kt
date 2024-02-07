package com.example.api_list_app.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api_list_app.R
import com.example.api_list_app.api.Repository
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.BuyLink
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


    fun getBooks(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllCharacters()
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

/*
    fun getBooks(): List<Book> {
        return listOf(
            Book(
                "Rebecca Yarros",
                R.drawable.book1,
                309,
                500,
                "",
                BuyLink("Amazon", "https://www.amazon.com/dp/1649374178?tag=NYTBSREV-20"),
                "The second book in the Empyrean series. Violet Sorrengail 2019s next round of training might require her to betray the man she loves.",
                "21.00",
                "Red Tower",
                "IRON FLAME",

            ),
            Book(
                    "Rebecca Yarros",
                R.drawable.book1,
            309,
            500,
            "",
            BuyLink("Amazon", "https://www.amazon.com/dp/1649374178?tag=NYTBSREV-20"),
            "The second book in the Empyrean series. Violet Sorrengail 2019s next round of training might require her to betray the man she loves.",
            "21.00",
            "Red Tower",
            "IRON FLAME"
            )
        )
    }

 */

}