package com.example.harry_potter_app.tabs.books

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.components.Information
import com.example.harry_potter_app.components.ItemInformation
import com.example.harry_potter_app.data.book.manager.BookDataViewModel
import com.example.harry_potter_app.data.book.type.Book

@Composable
fun Books() {

    val viewModel = hiltViewModel<BookDataViewModel>()
    val books by viewModel.books.collectAsState()
    val loading by viewModel.loadingBooks.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    var selectedBook by remember { mutableStateOf<Book?>(null) }

    if(selectedBook !== null){
        selectedBook?.let { book ->
            ItemInformation(
                Information(
                    image = book.cover,
                    name = book.title,
                    information = listOf(
                        "Release date: ${book.releaseDate}",
                        "Pages: ${book.pages}",
                        "Description: ${book.description}"
                    )
                )
            )
        }
    }
    else{
        CardTabLayout(
            loading = loading,
            showRetry = showRetry,
            layoutTitleId = R.string.books,

            items = books.mapIndexed { index, book ->
                CardData(
                    title = book.title,
                    imgUrl = book.cover,
                    onClick = {
                        selectedBook = book
                    },
                    favorite = book.favorite,
                    addToFavoriteFunction = {
                        viewModel.addBookToFavorites(book.index)
                    },
                    removeFromFavoriteFunction = {
                        viewModel.removeBookFromFavorites(book.index)
                    }
                )
            },
            retryFunction = {
                viewModel.retryGettingBooks()
            }
        )
    }

}