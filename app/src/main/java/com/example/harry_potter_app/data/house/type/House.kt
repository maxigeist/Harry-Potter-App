package com.example.harry_potter_app.data.house.type

data class House(
    val house: String,
    val emoji: String,
    val founder:String,
    val colors: List<String>,
    val animal: String,
    val index: Int,
    var favorite: Boolean,
)