package com.example.harry_potter_app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.harry_potter_app.tabs.characters.Characters

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HarryPotterScreen.Characters.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 10.dp)
    ) {
        composable(route = HarryPotterScreen.Characters.name) {
            Characters()
        }
        composable(route = HarryPotterScreen.Houses.name) {
            //TODO component houses
        }
        composable(route = HarryPotterScreen.Books.name) {
            //TODO component books
        }
        composable(route = HarryPotterScreen.Settings.name) {
            //TODO component settings
        }

    }
}