package com.example.harry_potter_app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.harry_potter_app.tabs.characters.Characters
import com.example.harry_potter_app.tabs.favorites.Favorites
import com.example.harry_potter_app.tabs.houses.Houses
import com.example.harry_potter_app.ui.theme.NavBarButtonsPadding

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HarryPotterScreen.Characters.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = NavBarButtonsPadding)
    ) {
        composable(route = HarryPotterScreen.Characters.name) {
            Characters()
        }
        composable(route = HarryPotterScreen.Houses.name) {
            Houses()
        }
        composable(route = HarryPotterScreen.Books.name) {
            //TODO component books
        }
        composable(route = HarryPotterScreen.Favorites.name) {
            Favorites()
        }

    }
}