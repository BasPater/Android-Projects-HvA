package com.example.level5task1.ui.screens

sealed class Screens(val route: String) {
    object CreateProfileScreen: Screens("create_profile_screen")
    object ProfileScreen: Screens("profile_screen")
}
