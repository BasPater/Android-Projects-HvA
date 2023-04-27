package com.example.level5task2.ui.screens

sealed class Screens(val route: String) {
    object HomeScreen: Screens("home_screen")
    object PlayQuizScreen: Screens("play_quiz_screen")
}