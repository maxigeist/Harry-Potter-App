package com.example.harry_potter_app.tabs.characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.data.character.manager.CharacterDataViewModel
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.components.Information
import com.example.harry_potter_app.components.ItemInformation
import com.example.harry_potter_app.data.character.type.Character

@Composable
fun Characters() {
    val viewModel = hiltViewModel<CharacterDataViewModel>()

    var selectedCharacter by remember { mutableStateOf<Character?>(null) }

    val characters by viewModel.characters.collectAsState()
    val loading by viewModel.loadingCharacters.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    if(selectedCharacter !== null){
        selectedCharacter?.let { character ->
            ItemInformation(
                Information(
                    image = character.image,
                    name = character.fullName,
                    information = listOf(
                        "Hogwarts house: ${character.hogwartsHouse}",
                        "Interpreted by: ${character.interpretedBy}"
                    )
                )
            )
        }
    }
    else{
        CardTabLayout(
            loading = loading,
            showRetry = showRetry,
            layoutTitleId = R.string.characters,
            items = characters.mapIndexed { index, character ->
                CardData(
                    title = character.nickname,
                    imgUrl = character.image,
                    onClick = {
                        selectedCharacter = character
                    },
                    favorite = character.favorite,
                    addToFavoriteFunction = {
                        viewModel.addCharacterToFavorites(index)
                    },
                    removeFromFavoriteFunction = {
                        viewModel.removeCharacterFromFavorites(index)
                    }
                )
            },
            retryFunction = {
                viewModel.retryGettingCharacters()
            }
        )
    }
}
