package com.example.level6task2.ui.screens

sealed class Screens(val route: String) {
    object SearchView: Screens("search_view")
    object MovieDetailScreen: Screens("detail_screen")
}
