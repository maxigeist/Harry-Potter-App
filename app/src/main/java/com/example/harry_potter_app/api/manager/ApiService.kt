package com.example.harry_potter_app.api.manager

import retrofit.http.GET
import retrofit.Call
import com.example.harry_potter_app.data.character.type.Character
import com.example.harry_potter_app.data.house.type.House

interface ApiService {

    @GET("characters")
    fun getCharacters(): Call<List<Character>>

    @GET("houses")
    fun getHouses(): Call<List<House>>
}