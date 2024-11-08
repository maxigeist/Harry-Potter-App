package com.example.harry_potter_app.remote.storage


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteCharacters")
data class FavoriteCharacter(
    @PrimaryKey
    val index:Int,
)

@Entity(tableName = "FavoriteHouses")
data class FavoriteHouse(
    @PrimaryKey
    val index:Int,
)

@Entity(tableName = "FavoriteBooks")
data class FavoriteBook(
    @PrimaryKey
    val index:Int,
)