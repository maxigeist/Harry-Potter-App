package com.example.harry_potter_app.data.house.manager

import androidx.lifecycle.ViewModel
import com.example.harry_potter_app.data.house.type.House
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HouseDataViewModel @Inject constructor() : ViewModel(){
    private var _housesList = MutableStateFlow(listOf<House>())
    val housesList = _housesList.asStateFlow()


    fun fetchHouses() {
        //TODO fetch data from API
    }

    fun addHouseToFavorites(house: House) {
        //TODO add house to favorites
    }
}