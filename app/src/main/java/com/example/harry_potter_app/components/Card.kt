package com.example.harry_potter_app.components

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.harry_potter_app.R


data class CardData(
    val title: String,
    val imgUrl: String,
    val onClick: () -> Unit
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
                .clip(RoundedCornerShape(28.dp))  // Clip the column with rounded corners
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cardData.title,
                modifier = Modifier.padding(8.dp),
                fontSize = 22.sp,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = cardData.imgUrl,
                    modifier = Modifier
                        .size(300.dp)  // Set a fixed size for all images
                        .clip(RoundedCornerShape(8.dp)),// Optional: Add some rounding to the corners
                    contentDescription = "Image for ${cardData.title}",
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    error = painterResource(R.drawable.ic_launcher_background),
                )
            }
        }
    }
}