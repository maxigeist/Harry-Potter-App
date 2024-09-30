package com.example.harry_potter_app.navigation

enum class HarryPotterScreen {
    Characters,
    Books,
    Houses,
    Favorites

}

val basePages = listOf(
    HarryPotterScreen.Characters.name,
    HarryPotterScreen.Books.name,
    HarryPotterScreen.Houses.name,
    HarryPotterScreen.Favorites.name,
)