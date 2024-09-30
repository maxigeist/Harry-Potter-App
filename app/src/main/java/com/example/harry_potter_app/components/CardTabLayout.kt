package com.example.harry_potter_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harry_potter_app.R
import com.example.harry_potter_app.ui.theme.PaddingBig
import com.example.harry_potter_app.ui.theme.PaddingMedium
import com.example.harry_potter_app.ui.theme.PaddingSmall
import com.example.harry_potter_app.ui.theme.TabTitleTextSize

@Composable

fun CardTabLayout(
    loading: Boolean,
    showRetry: Boolean,
    layoutTitleId: Int,
    items: List<CardData>,
    retryFunction: () -> Unit
) {

    if (loading) {
        Loader()
    } else if (showRetry) {
        Button(
            buttonData = ButtonData(
                title = stringResource(id = R.string.retry),
                onClick = {
                    retryFunction()
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
                    text = stringResource(id = layoutTitleId),
                    modifier = Modifier
                        .padding(PaddingSmall),
                    fontSize = TabTitleTextSize,
                    fontFamily = FontFamily(
                        Font(R.font.harry)
                    ),
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(PaddingMedium),
                    verticalArrangement = Arrangement.spacedBy(PaddingBig),
                    horizontalArrangement = Arrangement.spacedBy(PaddingBig)
                ) {
                    items(count = items.size) { index ->
                        Card(
                            cardData = items[index]
                        )
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun CardTabLayoutPreview() {
    CardTabLayout(
        loading = false,
        showRetry = false,
        layoutTitleId = R.string.houses,
        items = listOf(
            CardData(
                title = "Gryffindor",
                imgUrl = "",
                emoji = "🦁",
                onClick = {},
                favorite = false,
                addToFavoriteFunction = {},
                removeFromFavoriteFunction = {}
            ),
            CardData(
                title = "Slytherin",
                imgUrl = "",
                emoji = "🐍",
                onClick = {},
                favorite = false,
                addToFavoriteFunction = {},
                removeFromFavoriteFunction = {}
            ),
            CardData(
                title = "Ravenclaw",
                imgUrl = "",
                emoji = "🦅",
                onClick = {},
                favorite = false,
                addToFavoriteFunction = {},
                removeFromFavoriteFunction = {}
            ),
            CardData(
                title = "Hufflepuff",
                imgUrl = "",
                emoji = "🦡",
                onClick = {},
                favorite = false,
                addToFavoriteFunction = {},
                removeFromFavoriteFunction = {}
            )
        ),
        retryFunction = {}
    )
}
