package com.example.harry_potter_app.data.favorite.manager

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.fetchCharactersFromApi
import com.example.harry_potter_app.local.storage.PreferencesKeys
import com.example.harry_potter_app.local.storage.dataStore
import com.example.harry_potter_app.local.storage.getFromDataStore
import com.example.harry_potter_app.local.storage.saveToDataStore
import com.example.harry_potter_app.remote.storage.HarryPotterDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    private var _favoriteHouses = MutableStateFlow("")
    val favoriteHouses = _favoriteHouses.asStateFlow()

    init {
        getFavoriteCharacters()
    }


    private fun getFavoriteCharacters() {
        // Launch a coroutine to collect the Flow from the remote storage
        viewModelScope.launch {
            val favoriteCharactersIndexes = getFavoriteCharactersIndexesFromRemoteStorage().firstOrNull() ?: listOf()

            fetchCharactersFromApi(
                onSuccess = { characters ->
                    _favoriteCharacters.value =
                        characters.filter { character -> favoriteCharactersIndexes.contains(character.index) }
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
    }



    private fun getFavoriteCharactersIndexesFromRemoteStorage(): Flow<List<Int>> {
        return harryPotterDatabase.favoriteCharacterDao().getAllCharacters().asFlow()
    }

}