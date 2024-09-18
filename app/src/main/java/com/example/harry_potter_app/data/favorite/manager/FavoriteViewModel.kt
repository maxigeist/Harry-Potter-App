package com.example.harry_potter_app.data.favorite.manager

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.fetchCharactersFromApi
import com.example.harry_potter_app.local.storage.PreferencesKeys
import com.example.harry_potter_app.local.storage.dataStore
import com.example.harry_potter_app.local.storage.getFromDataStore
import com.example.harry_potter_app.local.storage.saveToDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    private var _favoriteCharacters = MutableStateFlow(listOf<Character>())
    val favoriteCharacters = _favoriteCharacters.asStateFlow()
    private var _favoriteBooks = MutableStateFlow("")
    val favoriteBooks = _favoriteBooks.asStateFlow()
    private var _favoriteHouses = MutableStateFlow("")
    val favoriteHouses = _favoriteHouses.asStateFlow()


    private fun getFavoriteCharacters() {
        val favoriteCharactersIndexes = getFavoriteCharactersIndexesFromDataStore()
        fetchCharactersFromApi(
            onSuccess = {
                _favoriteCharacters.value =
                    it.filter { character -> favoriteCharactersIndexes.contains(character.index.toString()) }
            },
            onFail = {
                _favoriteCharacters.value = listOf()
            },
            loadingFinished = {
                // Do nothing
            },
            context = context
        )
    }



    private fun getFavoriteCharactersIndexesFromDataStore(): List<String> {
        var favoriteCharacterNames: List<String> = listOf()
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.FAVORITE_CHARACTER_INDEXES).collect {
                if (it != null) {
                    favoriteCharacterNames = it.split(",")
                }
            }
        }
        return favoriteCharacterNames
    }

}