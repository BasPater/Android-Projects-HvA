package com.example.level6task1.ui

import androidx.annotation.StringRes
import com.example.level6task1.R

sealed class PetsScreens(val route: String, @StringRes val labelResourceId: Int) {
    object CatsScreen: PetsScreens("cats", R.string.cats)
    object DogsScreen: PetsScreens("dogs", R.string.dogs)
}
