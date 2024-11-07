package com.example.harry_potter_app.data.book.manager

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.components.Toast
import com.example.harry_potter_app.data.book.type.Book
import com.example.harry_potter_app.data.fetchBooksFromApi
import com.example.harry_potter_app.data.fetchCharactersFromApi
import com.example.harry_potter_app.remote.storage.FavoriteBook
import com.example.harry_potter_app.remote.storage.FavoriteCharacter
import com.example.harry_potter_app.remote.storage.HarryPotterDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDataViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel(){

    private var _books = MutableStateFlow(listOf<Book>())
    val books = _books.asStateFlow()

    private val _loadingBooks = MutableStateFlow(false)
    val loadingBooks = _loadingBooks.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private val toast = Toast(context)
    private val harryPotterDatabase = HarryPotterDatabase.getDatabase(context)


    init {
        fetchBooks()
    }

    fun retryGettingBooks() {
        fetchBooks()
    }

    private fun fetchBooks() {
        _loadingBooks.value = true
        fetchBooksFromApi(
            onSuccess = {
                viewModelScope.launch {
                    getFavoriteBooks().asFlow().collect { favoriteBooks ->
                        val books = it.map { book ->
                            book.favorite = favoriteBooks.contains(book.index)
                            book
                        }
                        _books.emit(books)
                    }
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingBooks.value = false
            },
            context = context
        )

    }

    fun addBookToFavorites(bookIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteBookDao()
                .insert(FavoriteBook(index = bookIndex))
            toast.makeToast(context.getString(com.example.harry_potter_app.R.string.added_book_to_favorites))
            retryGettingBooks()
        }
    }

    fun removeBookFromFavorites(bookIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteBookDao()
                .delete(FavoriteBook(index = bookIndex))
            toast.makeToast(context.getString(com.example.harry_potter_app.R.string.removed_book_from_favorites))
            retryGettingBooks()
        }
    }

    private fun getFavoriteBooks() : LiveData<List<Int>> = harryPotterDatabase.favoriteBookDao().getAllBooks()
}