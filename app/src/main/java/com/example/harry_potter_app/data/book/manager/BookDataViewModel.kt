package com.example.harry_potter_app.data.book.manager

import androidx.lifecycle.ViewModel
import com.example.harry_potter_app.data.book.type.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BookDataViewModel @Inject constructor() : ViewModel(){
    private var _booksList = MutableStateFlow(listOf<Book>())
    val booksList = _booksList.asStateFlow()

    fun fetchBooks() {
        //TODO fetch data from API
    }
    fun addBooksToFavorites(book: Book) {
        //TODO add book to favorites
    }
}