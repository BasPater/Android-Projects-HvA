package com.example.level3task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level3task2.ui.screens.*
import com.example.level3task2.ui.theme.Level3Task2Theme
import com.example.level3task2.viewmodel.PortalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Level3Task2Theme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    RemindersNavHost(navController = navController, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun RemindersNavHost(
    navController: NavHostController, modifier: Modifier
) {
    val viewModel: PortalViewModel = viewModel()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = PortalScreens.ListScreen.name,
        modifier = modifier
    )
    {
        composable(PortalScreens.ListScreen.name) {
            PortalListScreen(navController, viewModel, modifier, context)
        }
        composable(PortalScreens.AddScreen.name) {
            PortalAddScreen(navController, viewModel, modifier, context)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Level3Task2Theme {
//        Greeting("Android")
//    }
//}