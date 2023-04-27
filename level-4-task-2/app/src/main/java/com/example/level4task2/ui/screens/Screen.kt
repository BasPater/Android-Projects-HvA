package com.example.level4task2.ui.screens

sealed class Screen(
    val route: String
) {
    object GameScreen: Screen("game_screen")
    object HistoryScreen: Screen("history_screen")
}