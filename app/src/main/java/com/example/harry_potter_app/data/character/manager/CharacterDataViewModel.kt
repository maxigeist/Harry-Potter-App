package com.example.harry_potter_app.data.character.manager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.api.manager.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.harry_potter_app.data.character.type.Character
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterDataViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiServiceImpl: ApiServiceImpl
) : ViewModel() {

    private val _loadingCharacters = MutableStateFlow(false)
    val loadingCharacters = _loadingCharacters.asStateFlow()

    private val _characters = MutableStateFlow(listOf<Character>())
    val characters = _characters.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    init {
        fetchCharacters()
    }

    fun retryLoadingRanking() {
        fetchCharacters()
    }



    private fun fetchCharacters() {
        _loadingCharacters.value = true
        apiServiceImpl.getCharacters(
            context = context,
            onSuccess = {
                viewModelScope.launch {
                    _characters.emit(it)
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingCharacters.value = false
            }
        )
    }

    fun addCharacterToFavorites(character: Character) {
        //TODO add character to favorites
    }
}