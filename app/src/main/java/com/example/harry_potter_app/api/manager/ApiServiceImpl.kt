package com.example.harry_potter_app.api.manager

import android.content.Context
import android.util.Log
import retrofit.Call
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.Toast
import com.example.harry_potter_app.data.book.type.Book
import com.example.harry_potter_app.data.character.type.Character
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.Callback
import javax.inject.Inject
import com.example.harry_potter_app.data.house.type.House
import retrofit.Response

class ApiServiceImpl @Inject constructor() {

    fun getCharacters(
        context: Context,
        onSuccess: (List<Character>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.api_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Character>> = service.getCharacters()

        call.enqueue(object : Callback<List<Character>> {
            override fun onResponse(response: Response<List<Character>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val jokes: List<Character> = response.body()
                    onSuccess(jokes)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                val toast = Toast(context)
                toast.makeToast(context.getString(R.string.cant_get_characters))
                onFail()
                loadingFinished()
            }
        })
    }


    fun getHouses(
        context: Context,
        onSuccess: (List<House>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.api_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<House>> = service.getHouses()

        call.enqueue(object : Callback<List<House>> {
            override fun onResponse(response: Response<List<House>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val jokes: List<House> = response.body()
                    onSuccess(jokes)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                val toast = Toast(context)
                toast.makeToast(context.getString(R.string.cant_get_houses))
                onFail()
                loadingFinished()
            }
        })
    }

    fun getBooks(
        context: Context,
        onSuccess: (List<Book>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.api_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Book>> = service.getBooks()

        call.enqueue(object : Callback<List<Book>> {
            override fun onResponse(response: Response<List<Book>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val jokes: List<Book> = response.body()
                    onSuccess(jokes)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                val toast = Toast(context)
                toast.makeToast(context.getString(R.string.cant_get_books))
                onFail()
                loadingFinished()
            }
        })
    }

}