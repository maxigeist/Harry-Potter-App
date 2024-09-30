package com.example.harry_potter_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

data class ButtonData(
    val leadingIcon: ImageVector? = null,
    val title: String,
    val trailingIcon: ImageVector? = null,
    val onClick: () -> Unit
)

@Composable
fun Button(buttonData: ButtonData){
    androidx.compose.material3.Button(
        onClick = buttonData.onClick, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {

            buttonData.leadingIcon?.let {
                Icon(imageVector = buttonData.leadingIcon, contentDescription = "")
            }

            Text(text = buttonData.title)

            buttonData.trailingIcon?.let{
                Icon(imageVector = buttonData.trailingIcon, contentDescription = "")
            }
        }
    }
}

@Preview
@Composable
fun  ButtonPreview() {
    Button(
        buttonData = ButtonData(
            title = "Button",
            onClick = {}
        )
    )
}