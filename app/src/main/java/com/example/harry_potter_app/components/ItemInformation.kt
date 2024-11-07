package com.example.harry_potter_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.harry_potter_app.R
import com.example.harry_potter_app.ui.theme.CornerShapeRadius


data class CharacterInfo(
    val image: String,
    val name: String,
    val information: List<String>
)

@Composable
fun ItemInformation(characterInformation: CharacterInfo) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
        ) {
            AsyncImage(
                model = characterInformation.image,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = CornerShapeRadius,
                            topEnd = CornerShapeRadius
                        )
                    ),
                contentScale = ContentScale.Crop,
                contentDescription = R.string.image_description.toString(),
                placeholder = painterResource(R.drawable.outlinewizard),
                error = painterResource(R.drawable.ic_launcher_background),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = characterInformation.name,
                fontSize = 50.sp,
                fontFamily = FontFamily(
                    Font(R.font.harry)
                )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                characterInformation.information.forEach {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}
