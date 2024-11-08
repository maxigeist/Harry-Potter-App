package com.example.harry_potter_app.tabs.houses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.components.Information
import com.example.harry_potter_app.components.ItemInformation
import com.example.harry_potter_app.data.house.manager.HouseDataViewModel
import com.example.harry_potter_app.data.house.type.House


@Composable
fun Houses() {
    val viewModel = hiltViewModel<HouseDataViewModel>()
    var selectedHouse by remember { mutableStateOf<House?>(null) }

    val houses by viewModel.houses.collectAsState()
    val loading by viewModel.loadingHouses.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    if(selectedHouse !== null){
        selectedHouse?.let { house ->
            ItemInformation(
                Information(
                    emoji = house.emoji,
                    name = house.house,
                    information = listOf(
                        "Founder: ${house.founder}",
                        "Colors: ${house.colors.joinToString(", ")}",
                        "Animal: ${house.animal}"
                    )
                )
            )
        }
    }
    else{
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
                        selectedHouse = house
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



}