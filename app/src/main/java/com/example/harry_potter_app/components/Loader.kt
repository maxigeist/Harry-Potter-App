package com.example.harry_potter_app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.harry_potter_app.ui.theme.PurpleGrey40
import com.example.harry_potter_app.ui.theme.PurpleGrey80


@Composable
fun Loader() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center),
            color = PurpleGrey40,
            trackColor = PurpleGrey80,
        )
    }
}