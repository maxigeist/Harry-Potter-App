package com.example.harry_potter_app.data.book.type

data class Book(
    val title: String,
    val index: Int,
    val releaseDate: String,
    val pages: Int,
    val cover: String,
    val description: String,
    var favorite: Boolean,
)