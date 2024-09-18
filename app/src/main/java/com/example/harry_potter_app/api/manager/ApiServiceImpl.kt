package com.example.harry_potter_app.api.manager

import android.content.Context
import retrofit.Call
import com.example.harry_potter_app.R
import com.example.harry_potter_app.data.character.type.Character
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.Callback
import javax.inject.Inject
import android.widget.Toast
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
                Toast.makeText(context, "Can't get characters", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }

}