package com.example.harry_potter_app.data.character.manager

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.harry_potter_app.data.character.type.Character
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CharacterDataViewModel @Inject constructor() : ViewModel() {

    private var _charactersList = MutableStateFlow(
        listOf<Character>(
            Character(
                "Harry Potter",
                "Gryffindor",
                "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/harry_potter.png",
                "Daniel Radcliff",
                "Harry"
            )
        )
    )
    val charactersList = _charactersList.asStateFlow()


    fun fetchCharacters() {
        //TODO fetch data from API
    }

    fun addCharacterToFavorites(character: Character) {
        //TODO add character to favorites
    }
}