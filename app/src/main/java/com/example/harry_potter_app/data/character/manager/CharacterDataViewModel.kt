package com.example.harry_potter_app.data.character.manager

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.components.Toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.fetchCharactersFromApi
import com.example.harry_potter_app.remote.storage.FavoriteCharacter
import com.example.harry_potter_app.remote.storage.HarryPotterDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterDataViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _characters = MutableStateFlow(listOf<Character>())
    val characters = _characters.asStateFlow()

    private val _loadingCharacters = MutableStateFlow(false)
    val loadingCharacters = _loadingCharacters.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private val toast = Toast(context)
    private val harryPotterDatabase = HarryPotterDatabase.getDatabase(context)

    init {
        fetchCharacters()
    }

    fun retryGettingCharacters() {
        fetchCharacters()
    }


    private fun fetchCharacters() {
        _loadingCharacters.value = true
        fetchCharactersFromApi(
            onSuccess = {
                viewModelScope.launch {
                    getFavoriteCharacters().asFlow().collect { favoriteCharacters ->
                        val characters = it.map { character ->
                            character.favorite = favoriteCharacters.contains(character.index)
                            character
                        }
                        _characters.emit(characters)
                    }
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingCharacters.value = false
            },
            context = context
        )
    }

    fun addCharacterToFavorites(characterIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteCharacterDao()
                .insert(FavoriteCharacter(index = characterIndex))
            toast.makeToast(context.getString(com.example.harry_potter_app.R.string.added_character_to_favorites))
            retryGettingCharacters()
        }

    }

    fun removeCharacterFromFavorites(characterIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteCharacterDao()
                .delete(FavoriteCharacter(index = characterIndex))
            toast.makeToast(context.getString(com.example.harry_potter_app.R.string.removed_character_from_favorites))
            retryGettingCharacters()
        }

    }


    private fun getFavoriteCharacters() : LiveData<List<Int>> = harryPotterDatabase.favoriteCharacterDao().getAllCharacters()
}