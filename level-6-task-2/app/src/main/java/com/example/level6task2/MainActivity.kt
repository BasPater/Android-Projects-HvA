package com.example.level6task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level6task2.ui.screens.Screens
import com.example.level6task2.ui.screens.detailView.MovieDetailScreen
import com.example.level6task2.ui.screens.searchView.MoviesViewModel
import com.example.level6task2.ui.screens.searchView.SearchView
import com.example.level6task2.ui.theme.Level6Task2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Level6Task2Theme() {
                val navController = rememberNavController()
                MovieNavHost(navController = navController)
            }
        }
    }
}

/**
 * You can see this as a nav_graph.xml in compose
 */
@Composable
fun MovieNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel: MoviesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.SearchView.route,
        modifier = modifier
    ) {
        //this one is from the previous step
        composable(Screens.SearchView.route) {
            SearchView(viewModel = viewModel, navController)
        }

        composable(Screens.MovieDetailScreen.route) {
            MovieDetailScreen(viewModel = viewModel, navController)
        }
    }
}