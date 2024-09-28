package com.example.harry_potter_app.tabs.characters

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.components.Card
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.data.character.manager.CharacterDataViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.Button
import com.example.harry_potter_app.components.ButtonData
import com.example.harry_potter_app.components.Loader
import com.example.harry_potter_app.data.favorite.manager.FavoriteViewModel

@Composable
fun Characters() {
    val viewModel = hiltViewModel<CharacterDataViewModel>()
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()

    val characters by viewModel.characters.collectAsState()
    val loading by viewModel.loadingCharacters.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    val favoriteCharacters by favoriteViewModel.favoriteCharacters.collectAsState()


    fun addCharacterToFavorite(index: Int) {
        Log.i("Characters", "addCharacterToFavorite: $index")
        Log.i("Characters", "favoriteCharacters: $favoriteCharacters")
        viewModel.addCharacterToFavorites(index)
    }


    if (loading) {
        Loader()
    } else if (showRetry) {
        Button(
            buttonData = ButtonData(
                title = stringResource(id = R.string.retry),
                onClick = {
                    viewModel.retryLoadingRanking()
                }
            )
        )
    } else {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.characters),
                    modifier = Modifier
                        .padding(8.dp),
                    fontSize = 72.sp,
                    fontFamily = FontFamily(
                        Font(R.font.harry)
                    ),
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(characters) { characterData ->
                        Card(
                            cardData = CardData(
                                characterData.nickname,
                                characterData.image,
                                onClick = { addCharacterToFavorite(characterData.index) })
                        )
                    }
                    items(favoriteCharacters) { characterData ->
                        Card(
                            cardData = CardData(
                                characterData.fullName,
                                characterData.image,
                                onClick = { addCharacterToFavorite(characterData.index) })
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewCharacters() {
    Characters()
}