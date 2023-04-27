package com.example.level5task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level5task1.ui.screens.CreateProfileScreen
import com.example.level5task1.ui.screens.ProfileScreen
import com.example.level5task1.ui.screens.Screens
import com.example.level5task1.ui.theme.Level5Task1Theme
import com.example.level5task1.ui.viewModel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProfileApp()
        }
    }
}

@Composable
fun ProfileApp() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        ProfileNavHost(navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ProfileNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel: ProfileViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.CreateProfileScreen.route,
        modifier = modifier
    ) {
        composable(route = Screens.CreateProfileScreen.route) {
            CreateProfileScreen(navController = navController, viewModel)
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(viewModel)
        }
    }
}