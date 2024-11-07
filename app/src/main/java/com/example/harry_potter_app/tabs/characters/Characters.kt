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
import com.example.harry_potter_app.components.CharacterInfo
import com.example.harry_potter_app.components.ItemInformation
import com.example.harry_potter_app.data.character.type.Character

@Composable
fun Characters() {
    val viewModel = hiltViewModel<CharacterDataViewModel>()

    var selectedCharacter by remember { mutableStateOf(Character("", "", "", "", "", 0, false)) }


    val characters by viewModel.characters.collectAsState()
    val loading by viewModel.loadingCharacters.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    if (selectedCharacter.fullName != ""){
        ItemInformation(
            CharacterInfo(
                image= selectedCharacter.image,
                name = selectedCharacter.fullName,
                information = listOf(
                    "Hogwarts house: " + selectedCharacter.hogwartsHouse,
                    "Interpreted by: " + selectedCharacter.interpretedBy
                )
            )
        )
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
