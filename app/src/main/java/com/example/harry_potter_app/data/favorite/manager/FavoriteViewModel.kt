package com.example.harry_potter_app.data.favorite.manager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.fetchCharactersFromApi
import com.example.harry_potter_app.data.fetchHousesFromApi
import com.example.harry_potter_app.data.house.type.House
import com.example.harry_potter_app.remote.storage.FavoriteCharacter
import com.example.harry_potter_app.remote.storage.HarryPotterDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    private val harryPotterDatabase = HarryPotterDatabase.getDatabase(context)
    private var _favoriteCharacters = MutableStateFlow(listOf<Character>())
    val favoriteCharacters = _favoriteCharacters.asStateFlow()
    private var _favoriteBooks = MutableStateFlow("")
    val favoriteBooks = _favoriteBooks.asStateFlow()

    private var _favoriteHouses = MutableStateFlow(listOf<House>())
    val favoriteHouses = _favoriteHouses.asStateFlow()

    private val _loadingFavorites = MutableStateFlow(false)
    val loadingFavorites = _loadingFavorites.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    init {
        getFavoriteCharacters()
        getFavoriteHouses()
    }


    private fun getFavoriteHouses() {
        _loadingFavorites.value = true
        viewModelScope.launch {
            val favoriteHousesIndexes = getFavoriteHousesIndexesFromRemoteStorage().firstOrNull() ?: listOf()
            fetchHousesFromApi(
                onSuccess = { houses ->
                    _favoriteHouses.value =
                        houses.filter { house -> favoriteHousesIndexes.contains(house.index) }
                },
                onFail = {
                    _showRetry.value = true
                },
                loadingFinished = {
                    _loadingFavorites.value = false
                },
                context = context
            )
        }
    }

    private fun getFavoriteCharacters() {
        _loadingFavorites.value = true
        viewModelScope.launch {
            val favoriteCharactersIndexes = getFavoriteCharactersIndexesFromRemoteStorage().firstOrNull() ?: listOf()

            fetchCharactersFromApi(
                onSuccess = { characters ->
                    _favoriteCharacters.value =
                        characters.filter { character -> favoriteCharactersIndexes.contains(character.index) }
                },
                onFail = {
                    _showRetry.value = true
                },
                loadingFinished = {
                    _loadingFavorites.value = false
                },
                context = context
            )
        }
    }

    private fun getFavoriteCharactersIndexesFromRemoteStorage(): Flow<List<Int>> {
        return harryPotterDatabase.favoriteCharacterDao().getAllCharacters().asFlow()
    }

    private fun getFavoriteHousesIndexesFromRemoteStorage(): Flow<List<Int>> {
        return harryPotterDatabase.favoriteHouseDao().getAllHouses().asFlow()
    }

    fun removeCharacterFromFavorites(characterIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteCharacterDao().delete(FavoriteCharacter(index = characterIndex))
            getFavoriteCharacters()
        }
    }

    fun retryGetFavorites() {
        getFavoriteCharacters()
        getFavoriteHouses()
    }

}