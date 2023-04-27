package com.example.level6task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.level6task1.ui.PetsScreens
import com.example.level6task1.ui.screens.catsScreen.CatsScreen
import com.example.level6task1.ui.screens.dogScreen.DogsScreen
import com.example.level6task1.ui.theme.Level6Task1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Level6Task1Theme {
                Level6Task1App()
            }
        }
    }
}

@Composable
fun Level6Task1App() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        bottomBar = {
            BottomNav(navController)
        }
    ) { innerPadding ->
        NavHost(navController, innerPadding)
    }
}

@Composable
fun BottomNav(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val screens = listOf(
            PetsScreens.CatsScreen,
            PetsScreens.DogsScreen,
        )
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(stringResource(screen.labelResourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun NavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController,
        startDestination = PetsScreens.CatsScreen.route,
        Modifier.padding(innerPadding))
    {
        composable(PetsScreens.CatsScreen.route) {
            CatsScreen()
        }

        composable(PetsScreens.DogsScreen.route) {
            DogsScreen()
        }
    }
}

@Composable
fun TestText(navController: NavHostController, modifier: Modifier = Modifier) {
    //TODO: we change this code later
    Text("It works! - remove this text later")
}
