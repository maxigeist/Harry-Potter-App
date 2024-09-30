package com.example.harry_potter_app.data.house.manager

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.harry_potter_app.components.Toast
import com.example.harry_potter_app.data.fetchHousesFromApi
import com.example.harry_potter_app.data.house.type.House
import com.example.harry_potter_app.remote.storage.FavoriteCharacter
import com.example.harry_potter_app.remote.storage.FavoriteHouse
import com.example.harry_potter_app.remote.storage.HarryPotterDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseDataViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel(){
    private var _houses = MutableStateFlow(listOf<House>())
    val houses = _houses.asStateFlow()

    private val _loadingHouses = MutableStateFlow(false)
    val loadingHouses = _loadingHouses.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private val harryPotterDatabase = HarryPotterDatabase.getDatabase(context)

    private val toast = Toast(context)

    init {
        fetchHouses()
    }

    fun retryGetHouses() {
        fetchHouses()
    }

    private fun fetchHouses() {
        _loadingHouses.value = true
        fetchHousesFromApi(
            onSuccess = {
                Log.i("Houses", "Houses: $it")
                viewModelScope.launch {
                    getFavoriteHouses().asFlow().collect { favoriteHouses ->
                        val houses = it.map { house ->
                            house.favorite = favoriteHouses.contains(house.index)
                            house
                        }
                        _houses.emit(houses)
                    }
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingHouses.value = false
            },
            context = context
        )
    }

    fun addHouseToFavorites(houseIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteHouseDao()
                .insert(FavoriteHouse(index = houseIndex))
            toast.makeToast("Added house to favorites")
            retryGetHouses()

        }
    }

    fun removeHouseFromFavorites(houseIndex: Int) {
        viewModelScope.launch {
            harryPotterDatabase.favoriteHouseDao()
                .delete(FavoriteHouse(index = houseIndex))
            toast.makeToast("Removed house from favorites")
            retryGetHouses()
        }
    }

    private fun getFavoriteHouses() = harryPotterDatabase.favoriteHouseDao().getAllHouses()
}