package com.example.harry_potter_app.remote.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteCharacterDao {

    @Insert
    suspend fun insert(characterIndex: FavoriteCharacter)

    @Delete
    suspend fun delete(characterIndex: FavoriteCharacter)

    @Query("SELECT * FROM FavoriteCharacters")
    fun getAllCharacters(): LiveData<List<Int>>

}

@Dao
interface FavoriteHouseDao {

    @Insert
    suspend fun insert(houseIndex: FavoriteHouse)

    @Delete
    suspend fun delete(houseIndex: FavoriteHouse)

    @Query("SELECT * FROM FavoriteHouses")
    fun getAllHouses(): LiveData<List<Int>>

}