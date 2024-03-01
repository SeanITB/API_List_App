package com.example.api_list_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api_list_app.api.Repository
import com.example.api_list_app.model.Book
import com.example.api_list_app.model.BookDetail
import com.example.api_list_app.model.Data
import com.example.api_list_app.navigation.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BocksViewModel: ViewModel() {
    private var repository = Repository()

    //Api data
    private val _loadingApi = MutableLiveData(true)
    val loadingApi = _loadingApi

    //home recent bocks
    private val _booksByGenderRecent = MutableLiveData<Data>()
    val booksByGenderRecent = _booksByGenderRecent

    // Search home books
    var booksOriginalReccent = Data(emptyList(), "", 0)
    private var searchBooksRecent : List<Book> = emptyList()
    private val _searchTextHome = MutableLiveData("")
    val searchTextHome = _searchTextHome
    private val _isSearchingHome = MutableLiveData(false)
    val isSearchingHome = _isSearchingHome


    //list books by gender
    private val _booksByGender = MutableLiveData<Data>()
    val booksByGender = _booksByGender

    // Search list books by gender
    var booksOriginal = Data(emptyList(), "", 0)
    private var searchBooks : List<Book> = emptyList()
    private val _searchText = MutableLiveData("")
    val searchText = _searchText
    private val _isSearching = MutableLiveData(false)
    val isSearching = _isSearching


    //for detail Book
    private val _bookForDetail = MutableLiveData<BookDetail>()
    val bookForDetail = _bookForDetail




    //Database favorites
    private val _loadingDB = MutableLiveData(true)
    val loadingDB = _loadingDB
    private val _isFavorite = MutableLiveData(false)
    val isFavorite = _isFavorite
    private val _favorites = MutableLiveData<List<Book>>()
    val favorites = _favorites
    //Search for fav
    var booksOriginalFav : List<Book> = emptyList()
    private var searchFav : List<Book> = emptyList()
    private val _searchTextFav = MutableLiveData("")
    val searchTextFav = _searchTextFav
    private val _isSearchingFav = MutableLiveData(false)
    val isSearchingFav = _isSearchingFav


    //Database toRead
    private val _loadingTR = MutableLiveData(true)
    val loadingTR = _loadingTR
    private val _isTR = MutableLiveData(false)
    val isTR = _isTR
    private val _tr= MutableLiveData<List<Book>>()
    val toRead = _tr

    //Database reading
    private val _loadingReading = MutableLiveData(true)
    val loadingReading = _loadingReading
    private val _isReading = MutableLiveData(false)
    val isReading = _isReading
    private val _Reading = MutableLiveData<List<BookDetail>>()
    val Reading = _Reading

    //Database read
    private val _loadingRead = MutableLiveData(true)
    val loadingRead = _loadingRead
    private val _isRead = MutableLiveData(false)
    val isRead = _isRead
    private val _read = MutableLiveData<List<BookDetail>>()
    val read = _read

    var actualScreen by mutableStateOf("homeScreen")
        private set

    var previusScreen by mutableStateOf("homeScreen")
        private set

    var initialScreen by mutableStateOf("homeScreen")
        private set


    var title by mutableStateOf("home")
        private set

    var expanded by mutableStateOf(false)
        private set

    var bookGender by mutableStateOf("recent")
        private set


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
    fun searchDependingOnTheScreen(searchText: String) {
        when (this.actualScreen) {
            "favoriteScreen" -> onSearchTextChangeFavorites(searchText)
            "homeScreen" -> onSearchTextChangeHome(searchText)
            else -> onSearchTextChangeList(searchText)
        }
    }

    fun onSearchTextChangeHome(value: String) {
        this.searchBooksRecent = booksOriginalReccent.books
        println("fount of RECENT: "+searchBooksRecent)
        this._searchTextHome.value = value
        this.searchBooksRecent = this.searchBooksRecent?.filter { it.title.contains(value, true)  }!!
        this._booksByGenderRecent.value = Data(searchBooksRecent, booksByGenderRecent.value!!.status, booksByGenderRecent.value!!.total)
    }

    fun onSearchTextChangeList(value: String) {
        this.searchBooks = booksOriginal.books
        this._searchText.value = value
        this.searchBooks = this.searchBooks?.filter { it.title.contains(value, true)  }!!
        this._booksByGender.value = Data(searchBooks, booksByGender.value!!.status, booksByGender.value!!.total)
    }

    fun onSearchTextChangeFavorites(value: String) {
        this.searchFav = booksOriginalFav
        this._searchTextFav.value = value
        this.searchFav = this.searchFav?.filter { it.title.contains(value, true)  }!!
        this._favorites.value = searchFav
    }

    /*
    fun onSearchTextChangeLiberi(value: String) {
        println("search in HOME")
        searchBooks = booksOriginal.books
        //println("text: "+value)
        this._searchText.value = value
        this.searchBooks = this.searchBooks?.filter { it.title.contains(value, true)  }!!
        //println("Lista: "+searchBooks?.size)
        _books.value = Data(searchBooks, books.value!!.status, books.value!!.total)
        //this._isSearching.value = true
    }

     */

    fun changeIsSearching(value: Boolean) {
        this._isSearching.value = value
    }

    fun changeIdBook(value: String) {
        this.idBook = value
    }

    fun changeExpanded (value: Boolean) {
        this.expanded = value
    }

    fun changeActualScreen(value: String) {
        this.actualScreen = value
    }

    fun changePreviusScreen(value: String) {
        this.previusScreen = value
    }

    fun changeInitialScreen(value: String) {
        this.initialScreen = value
    }

    fun changeTitele(value: String) {
        this.title = value
    }

    fun getRout(): String {
        return  when(previusScreen) {
            "listScreen", "search"  -> Routes.ListScreen.route
            "favoriteScreen" -> Routes.FavoriteScreen.route
            else -> Routes.HomeScreen.route
        }
    }

    fun getBookById(i: String): Book {
        println("ID book booksVM: "+i)
        println("result size booksVM BEFOR: "+booksByGender.value?.books?.size)
        val result = if (actualScreen == "listScreen") {
            booksByGender.value!!.books.filter { it.id == i } // filtrar per id
            //println("result size booksVM AFTER: " + result.size)
        } else {
            booksByGenderRecent.value!!.books.filter { it.id == i }
        }
        return result[0] //because the result is a list with only a element
    }

    fun getBooksRecent(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getRecent()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    _booksByGenderRecent.value = response.body()
                    booksOriginalReccent = booksByGenderRecent.value!!.copy()
                    _loadingApi.value = false
                }
                else {
                    Log.e("ERROR : ", response.message())
                }
            }
        }
    }

    fun getBooksByGender(gender: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getGender(gender)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    _booksByGender.value = response.body()
                    booksOriginal = booksByGender.value!!.copy()
                    _loadingApi.value = false
                }
                else {
                    Log.e("ERROR : ", response.message())
                }
            }
        }
    }


    fun getBook(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOneBook(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    _bookForDetail.value = response.body()
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
                booksOriginalFav = favorites.value!!.toList()
                _loadingDB.value = false
            }
        }
    }

    fun isFavorite(b: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isFavorite(b)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveFavorite(b: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsFavorite(b)
            _isFavorite.postValue(true)
        }
    }

    fun deleteFavorite(b: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavorite(b)
            _isFavorite.postValue(false)
        }
    }
/*
    fun getToRead(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getToRead()
            withContext(Dispatchers.Main) {
                _tr.value = response
                _loadingTR.value = false
            }
        }
    }

    fun isToRead(Book: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isToRead(Book)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveAsToRead(b: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsToRead(b)
            _isFavorite.postValue(true)
        }
    }

    fun deleteToRead(b: Book) {
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

    fun isReading(Book: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isReading(Book)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveAsReading(b: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsReading(b)
            _isFavorite.postValue(true)
        }
    }

    fun deleteReading(b: Book) {
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

    fun isRead(Book: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isRead(Book)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveAsRead(b: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsRead(b)
            _isFavorite.postValue(true)
        }
    }

    fun deleteRead(b: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteRead(b)
            _isFavorite.postValue(false)
        }
    }

 */

}