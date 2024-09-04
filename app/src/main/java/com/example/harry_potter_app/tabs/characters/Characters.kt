package com.example.harry_potter_app.tabs.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
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

@Composable
fun Characters() {
    val viewModel = hiltViewModel<CharacterDataViewModel>()
    val charactersList by viewModel.charactersList.collectAsState()


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
            items(charactersList) { characterData ->
                Card(cardData = CardData(characterData.nickname, characterData.image, onClick = {}))
            }
        }
    }
}

@Composable
@Preview
fun PreviewCharacters() {
    Characters()
}