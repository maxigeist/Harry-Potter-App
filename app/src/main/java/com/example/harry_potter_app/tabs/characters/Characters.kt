package com.example.harry_potter_app.tabs.characters

import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.harry_potter_app.R
import com.example.harry_potter_app.data.favorite.manager.FavoriteViewModel
import com.example.harry_potter_app.ui.theme.Pink40
import com.example.harry_potter_app.ui.theme.PurpleGrey40
import com.example.harry_potter_app.ui.theme.PurpleGrey80

@Composable
fun Characters() {
    val viewModel = hiltViewModel<CharacterDataViewModel>()

    val characters by viewModel.characters.collectAsState()
    val loading by viewModel.loadingCharacters.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()


    if (loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                color = PurpleGrey40,
                trackColor = PurpleGrey80,
            )
        }
    } else if (showRetry) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(PurpleGrey80).fillMaxSize()
        ) {
            Button(onClick = { viewModel.retryLoadingRanking() }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(characters) { characterData ->
                    Card(
                        cardData = CardData(
                            characterData.nickname,
                            characterData.image,
                            onClick = {})
                    )
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