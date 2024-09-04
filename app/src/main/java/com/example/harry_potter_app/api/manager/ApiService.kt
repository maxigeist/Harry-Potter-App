package com.example.harry_potter_app.api.manager

import retrofit.http.GET
import retrofit.Call
import com.example.harry_potter_app.data.character.type.Character

interface ApiService {

    @GET("characters")
    fun getRanking(): Call<List<Character>>
}