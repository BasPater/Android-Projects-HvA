package com.example.level4task1.ui.screens

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen("home_screen")
    object AddGameScreen: Screen("add_game_screen")
}
