package com.example.harry_potter_app.data

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.api.manager.ApiServiceImpl
import com.example.harry_potter_app.data.character.type.Character
import kotlinx.coroutines.launch

private val apiServiceImpl: ApiServiceImpl = ApiServiceImpl()

fun fetchCharactersFromApi(onSuccess: (List<Character>) -> Unit, onFail: () -> Unit, loadingFinished:() -> Unit, context: Context) {
    apiServiceImpl.getCharacters(
        context = context,
        onSuccess = {
            onSuccess(it)
        },
        onFail = {
            onFail()
        },
        loadingFinished = {
            loadingFinished()
        }
    )
}