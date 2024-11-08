package com.example.harry_potter_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.harry_potter_app.R
import com.example.harry_potter_app.ui.theme.CornerShapeRadius
import com.example.harry_potter_app.ui.theme.EmojiFontSize
import com.example.harry_potter_app.ui.theme.ImageSize
import com.example.harry_potter_app.ui.theme.MediumFontSize
import com.example.harry_potter_app.ui.theme.PaddingSmall


data class CardData(
    val title: String,
    val imgUrl: String,
    val emoji: String? = null,
    val onClick: () -> Unit,
    val favorite: Boolean,
    val addToFavoriteFunction: () -> Unit,
    val removeFromFavoriteFunction: () -> Unit
)

@Composable
fun Card(cardData: CardData) {
    Surface(
        onClick = cardData.onClick
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(CornerShapeRadius))
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
            ) {
                if (cardData.emoji != null) {
                    Text(
                        text = cardData.emoji,
                        fontSize = EmojiFontSize,
                        fontFamily = FontFamily(
                            Font(R.font.harry)
                        ),
                        color = Color.White
                    )
                }
                else {
                    AsyncImage(
                        model = cardData.imgUrl,
                        modifier = Modifier
                            .size(ImageSize)
                            .clip(
                                RoundedCornerShape(
                                    topStart = CornerShapeRadius,
                                    topEnd = CornerShapeRadius
                                )
                            ),
                        contentScale = ContentScale.Fit,
                        contentDescription = R.string.image_description.toString(),
                        placeholder = painterResource(R.drawable.outlinewizard),
                        error = painterResource(R.drawable.ic_launcher_background),
                    )
                }
            }
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = cardData.title,
                        modifier = Modifier.padding(PaddingSmall),
                        fontSize = MediumFontSize,
                        fontFamily = FontFamily(
                            Font(R.font.harry)
                        ),
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically),
                            onClick = if (cardData.favorite) cardData.removeFromFavoriteFunction else cardData.addToFavoriteFunction,
                        ) {
                            Image(
                                painter = painterResource(id = if (cardData.favorite) R.drawable.filledheart else R.drawable.outlinedheart),
                                modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                                contentDescription = R.string.heart_description.toString(),
                            )
                        }
                    }
                }
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    Card(
        cardData = CardData(
            title = "Harry Potter",
            imgUrl = "https://hp-api.herokuapp.com/images/harry.jpg",
            onClick = {},
            favorite = false,
            addToFavoriteFunction = {},
            removeFromFavoriteFunction = {}
        )
    )
}