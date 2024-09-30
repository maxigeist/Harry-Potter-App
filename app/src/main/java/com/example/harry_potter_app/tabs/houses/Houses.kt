package com.example.harry_potter_app.tabs.houses

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.data.house.manager.HouseDataViewModel


@Composable
fun Houses() {

    val viewModel = hiltViewModel<HouseDataViewModel>()
    val houses by viewModel.houses.collectAsState()
    val loading by viewModel.loadingHouses.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()
    CardTabLayout(
        loading = loading,
        showRetry = showRetry,
        layoutTitleId = R.string.houses,

        items = houses.mapIndexed { index, house ->
            CardData(
                title = house.house,
                emoji = house.emoji,
                imgUrl = "",
                onClick = {
                    //TODO
                },
                favorite = house.favorite,
                addToFavoriteFunction = {
                    viewModel.addHouseToFavorites(house.index)
                },
                removeFromFavoriteFunction = {
                    viewModel.removeHouseFromFavorites(house.index)
                }
            )
        },
        retryFunction = {
            viewModel.retryGetHouses()
        }
    )

}