package com.example.api_list_app.viewModel

import com.example.api_list_app.model.Book
import com.example.api_list_app.model.BuyLink

class BocksViewModel {

    // toDo: hard code
    private var books: MutableList<Book> = mutableListOf()
    fun getBook(): List<Book> {
        return listOf(
            Book(
                "Rebecca Yarros",
                "https://storage.googleapis.com/du-prd/books/images/9781649374042.jpg",
                309,
                500,
                "",
                BuyLink("Amazon", "https://www.amazon.com/dp/1649374178?tag=NYTBSREV-20"),
                "The second book in the Empyrean series. Violet Sorrengail 2019s next round of training might require her to betray the man she loves.",
                "21.00",
                "Red Tower",
                "IRON FLAME"
            ),
            Book(
                    "Rebecca Yarros",
            "https://storage.googleapis.com/du-prd/books/images/9781649374042.jpg",
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

}