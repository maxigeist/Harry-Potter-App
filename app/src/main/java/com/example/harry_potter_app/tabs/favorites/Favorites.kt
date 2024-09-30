package com.example.harry_potter_app.tabs.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.data.character.manager.CharacterDataViewModel
import com.example.harry_potter_app.data.favorite.manager.FavoriteViewModel

@Composable
fun Favorites() {
    val viewModel = hiltViewModel<FavoriteViewModel>()
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsState()
    val favoriteHouses by viewModel.favoriteHouses.collectAsState()
    val loading by viewModel.loadingFavorites.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()


    val items = favoriteCharacters.mapIndexed { index, character ->
        CardData(
            title = character.nickname,
            imgUrl = character.image,
            onClick = {
            },
            favorite = true,
            addToFavoriteFunction = {
            },
            removeFromFavoriteFunction = {
                viewModel.removeCharacterFromFavorites(character.index)
            }
        )
    } + favoriteHouses.mapIndexed { index, house ->
        CardData(
            title = house.house,
            imgUrl = "",
            emoji = house.emoji,
            onClick = {

            },
            favorite = true,
            addToFavoriteFunction = {

            },
            removeFromFavoriteFunction = {
            }
        )
    }

    CardTabLayout(
        loading = loading,
        showRetry = showRetry,
        layoutTitleId = R.string.favorites,
        items = items,
        retryFunction = {
            viewModel.retryGetFavorites()
        }
    )


}