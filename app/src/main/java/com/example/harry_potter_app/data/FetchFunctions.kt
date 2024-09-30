package com.example.harry_potter_app.data

import android.content.Context
import com.example.harry_potter_app.api.manager.ApiServiceImpl
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.house.type.House

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

fun fetchHousesFromApi(onSuccess: (List<House>) -> Unit, onFail: () -> Unit, loadingFinished:() -> Unit, context: Context) {
    apiServiceImpl.getHouses(
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