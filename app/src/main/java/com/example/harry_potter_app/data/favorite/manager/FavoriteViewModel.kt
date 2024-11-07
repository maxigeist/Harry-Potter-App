package com.example.harry_potter_app.data.favorite.manager

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.components.Toast
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.fetchCharactersFromApi
import com.example.harry_potter_app.data.fetchHousesFromApi
import com.example.harry_potter_app.data.house.type.House
import com.example.harry_potter_app.remote.storage.FavoriteCharacter
import com.example.harry_potter_app.remote.storage.HarryPotterDatabase
import com.example.harry_potter_app.security.BiometricAuthManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull

@HiltViewModel(assistedFactory = FavoriteViewModel.FavoriteViewModelFactory::class)
class FavoriteViewModel @AssistedInject constructor(
    @Assisted private val context: Context,
    @ApplicationContext val applicationContext: Context,
    val biometricAuthManager: BiometricAuthManager,
) : ViewModel() {

    private var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()
    private val harryPotterDatabase = HarryPotterDatabase.getDatabase(applicationContext)
    private var _favoriteCharacters = MutableStateFlow(listOf<Character>())
    val favoriteCharacters = _favoriteCharacters.asStateFlow()
    private var _favoriteBooks = MutableStateFlow("")
    val favoriteBooks = _favoriteBooks.asStateFlow()
    private val toast = Toast(applicationContext)


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
                context = applicationContext
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
                context = applicationContext
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

    fun biometricAuthentication(){
        Log.i("FavoriteViewModel", "biometricAuthentication called")
        biometricAuthManager.authenticate(
            context,
            onError = {
                _isAuthenticated.value = false
                toast.makeToast("An error occurred during the authentication")
            },
            onSuccess = {
                _isAuthenticated.value = true
            },
            onFail = {
                _isAuthenticated.value = false
                toast.makeToast("Authentication failed")
            }
        )
    }
    @AssistedFactory
    interface FavoriteViewModelFactory {
        fun create(context: Context): FavoriteViewModel
    }
}