package com.example.harry_potter_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.harry_potter_app.ui.theme.FourDivision
import com.example.harry_potter_app.ui.theme.PaddingBig
import com.example.harry_potter_app.ui.theme.PaddingMedium
import com.example.harry_potter_app.ui.theme.SixDivision
import com.example.harry_potter_app.ui.theme.SmallFontSize
import com.example.harry_potter_app.ui.theme.SmallInformationTitleTextSize


data class Information(
    val image: String? = null,
    val emoji: String? = null,
    val name: String,
    val information: List<String>
)

@Composable
fun ItemInformation(information: Information) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(SixDivision)
        ) {
            if (information.emoji != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = information.emoji,
                        fontSize = EmojiFontSize,
                        fontFamily = FontFamily(
                            Font(R.font.harry)
                        ),
                        color = Color.White
                    )
                }

            }
            else{
                AsyncImage(
                    model = information.image,
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
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(FourDivision)
                .padding(PaddingMedium),
                verticalArrangement = Arrangement.spacedBy(PaddingBig),
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = information.name,
                fontSize = SmallInformationTitleTextSize,
                fontFamily = FontFamily(
                    Font(R.font.harry)
                )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingMedium)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(PaddingMedium)
            ) {
                information.information.forEach {
                    Text(
                        text = it,
                        fontSize = SmallFontSize,
                        fontFamily = FontFamily(
                            Font(R.font.harry)
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemInformationPreview() {
    ItemInformation(
        information = Information(
            emoji = "🧙",
            name = "Harry Potter",
            information = listOf(
                "Harry James Potter is a fictional character and the titular protagonist in J. K. Rowling's series of eponymous novels.",
                "The majority of the books' plot covers seven years in the life of the orphan Harry, who, on his eleventh birthday, learns he is a wizard.",
                "Thus, he attends Hogwarts School of Witchcraft and Wizardry to practice magic under the guidance of the kindly headmaster Albus Dumbledore and other school professors along with his best friends Ron Weasley and Hermione Granger."
            )
        )
    )
}
