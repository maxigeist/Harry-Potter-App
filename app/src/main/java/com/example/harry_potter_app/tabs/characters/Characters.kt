package com.example.harry_potter_app.tabs.characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.data.character.manager.CharacterDataViewModel
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardTabLayout

@Composable
fun Characters() {
    val viewModel = hiltViewModel<CharacterDataViewModel>()

    val characters by viewModel.characters.collectAsState()
    val loading by viewModel.loadingCharacters.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()


    CardTabLayout(
        loading = loading,
        showRetry = showRetry,
        layoutTitleId = R.string.characters,
        items = characters.mapIndexed { index, character ->
            CardData(
                title = character.nickname,
                imgUrl = character.image,
                onClick = {
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
