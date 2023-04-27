package com.example.level3task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level3task1.ui.screens.GameRatingScreens
import com.example.level3task1.ui.screens.RatingScreen
import com.example.level3task1.ui.screens.StartScreen
import com.example.level3task1.ui.screens.SummaryScreen
import com.example.level3task1.ui.theme.Level3Task1Theme
import com.example.level3task1.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Level3Task1Theme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    GameNavHost(navController = navController, modifier = Modifier)
                }
            }
        }
    }
}


@Composable
private fun GameNavHost(
    navController: NavHostController, modifier: Modifier
) {
    val viewModel: GameViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = GameRatingScreens.StartScreen.name,
        modifier = modifier
    )
    {
        composable(GameRatingScreens.StartScreen.name) {
            StartScreen(navController, viewModel)
        }
        composable(GameRatingScreens.RatingScreen.name) {
            RatingScreen(navController, viewModel)
        }

        composable(GameRatingScreens.SummaryScreen.name) {
            SummaryScreen(navController, viewModel)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Level3Task1Theme {
        Greeting("Android")
    }
}