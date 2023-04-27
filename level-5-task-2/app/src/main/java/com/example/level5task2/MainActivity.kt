package com.example.level5task2

import android.content.Context
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
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level5task2.ui.screens.HomeScreen
import com.example.level5task2.ui.screens.PlayQuizScreen
import com.example.level5task2.ui.screens.Screens
import com.example.level5task2.ui.theme.Level5Task2Theme
import com.example.level5task2.viewModel.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this

        setContent {
            QuizApp(context)
        }
    }
}

@Composable
fun QuizApp(context: Context) {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        QuizNavHost(context, navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun QuizNavHost(context: Context, navController: NavHostController, modifier: Modifier = Modifier) {

    val viewModel: QuizViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        modifier = modifier
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screens.PlayQuizScreen.route) {
            PlayQuizScreen(navController = navController, viewModel = viewModel)
        }
    }
}