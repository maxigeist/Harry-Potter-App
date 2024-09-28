package com.example.harry_potter_app.remote.storage


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteCharacters")
data class FavoriteCharacter(
    @PrimaryKey(autoGenerate = true)
    val index:Int = 0,
)