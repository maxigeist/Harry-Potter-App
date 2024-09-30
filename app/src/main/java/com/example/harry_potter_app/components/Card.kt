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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.harry_potter_app.R


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
                .fillMaxWidth(0.4f)
                .fillMaxHeight(0.4f)
                .padding(0.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                if (cardData.emoji != null) {
                    Text(
                        text = cardData.emoji,
                        fontSize = 120.sp,
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
                            .size(240.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentDescription = "Image for ${cardData.title}",
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(R.drawable.ic_launcher_background),
                        error = painterResource(R.drawable.ic_launcher_background),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cardData.title,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 40.sp,
                    fontFamily = FontFamily(
                        Font(R.font.harry)
                    ),
                    color = Color.White
                )
                Surface(
                    onClick = if (cardData.favorite) cardData.removeFromFavoriteFunction else cardData.addToFavoriteFunction,
                ) {
                    Image(
                        painter = painterResource(id = if (cardData.favorite) R.drawable.filledheart else R.drawable.outlinedheart),
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary),
                    )
                }
            }

        }
    }
}