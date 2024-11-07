package com.example.harry_potter_app.tabs.books

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.data.book.manager.BookDataViewModel

@Composable
fun Books() {

    val viewModel = hiltViewModel<BookDataViewModel>()
    val books by viewModel.books.collectAsState()
    val loading by viewModel.loadingBooks.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    CardTabLayout(
        loading = loading,
        showRetry = showRetry,
        layoutTitleId = R.string.books,

        items = books.mapIndexed { index, book ->
            CardData(
                title = book.title,
                imgUrl = book.cover,
                onClick = {
                    //TODO
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