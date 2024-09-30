package com.example.harry_potter_app.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Toast @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun makeToast(text: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, length).show()
    }
}