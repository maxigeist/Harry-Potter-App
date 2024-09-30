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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harry_potter_app.R

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