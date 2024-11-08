package com.example.harry_potter_app.data.favorite.manager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.Toast
import com.example.harry_potter_app.data.book.type.Book
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.fetchBooksFromApi
import com.example.harry_potter_app.data.fetchCharactersFromApi
import com.example.harry_potter_app.data.fetchHousesFromApi
import com.example.harry_potter_app.data.house.type.House
import com.example.harry_potter_app.remote.storage.FavoriteBook
import com.example.harry_potter_app.remote.storage.FavoriteCharacter
import com.example.harry_potter_app.remote.storage.FavoriteHouse
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

    private var _favoriteBooks = MutableStateFlow(
        listOf<Book>())
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
        getFavoriteBooks()
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

    private fun getFavoriteBooks() {
        _loadingFavorites.value = true
        viewModelScope.launch {
            val favoriteBooksIndexes = getFavoriteBooksIndexesFromRemoteStorage().firstOrNull() ?: listOf()

            fetchBooksFromApi(
                onSuccess = { books ->
                    _favoriteBooks.value =
                        books.filter { book -> favoriteBooksIndexes.contains(book.index) }
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

    private fun getFavoriteBooksIndexesFromRemoteStorage(): Flow<List<Int>> {
        return harryPotterDatabase.favoriteBookDao().getAllBooks().asFlow()
    }

    fun removeCharacterFromFavorites(characterIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteCharacterDao().delete(FavoriteCharacter(index = characterIndex))
            getFavoriteCharacters()
        }
    }

    fun removeBookFromFavorites(bookIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteBookDao().delete(FavoriteBook(index = bookIndex))
            getFavoriteBooks()
        }
    }

    fun removeHouseFromFavorites(houseIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteHouseDao().delete(FavoriteHouse(index = houseIndex))
            getFavoriteHouses()
        }
    }

    fun retryGetFavorites() {
        getFavoriteCharacters()
        getFavoriteHouses()
        getFavoriteBooks()
    }

    fun biometricAuthentication(){
        biometricAuthManager.authenticate(
            context,
            onError = {
                _isAuthenticated.value = false
                toast.makeToast(context.getString(R.string.an_error_occurred_during_the_authentication))
            },
            onSuccess = {
                _isAuthenticated.value = true
            },
            onFail = {
                _isAuthenticated.value = false
                toast.makeToast(context.getString(R.string.authentication_failed))
            }
        )
    }
    @AssistedFactory
    interface FavoriteViewModelFactory {
        fun create(context: Context): FavoriteViewModel
    }
}