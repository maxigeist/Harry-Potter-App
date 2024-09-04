package com.example.harry_potter_app.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harry_potter_app.R
import com.example.harry_potter_app.ui.theme.Pink80
import com.example.harry_potter_app.components.Button
import com.example.harry_potter_app.components.Card
import com.example.harry_potter_app.components.CardData



@Composable
fun Home() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val cardDataList = listOf(
            CardData("Harry", "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/harry_potter.png", onClick = {}),
            CardData("Hermione", "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/hermione_granger.png", onClick = {}),
            CardData("Ron Weasley", "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/ron_weasley.png", onClick = {}),
            CardData("Fred Weasley", "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/fred_weasley.png", onClick = {})
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cardDataList) { cardData ->
                Card(cardData = cardData)
            }
        }
    }
    }


@Preview
@Composable
fun Preview(){
    Home()
}