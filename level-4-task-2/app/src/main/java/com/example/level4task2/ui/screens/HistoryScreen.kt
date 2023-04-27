package com.example.level4task2.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.level4task2.R
import com.example.level4task2.model.Game
import com.example.level4task2.model.GameViewModel
import com.example.level4task2.model.Moves

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(navController: NavController, viewModel: GameViewModel) {
    val context = LocalContext.current
    var games = viewModel.gameBacklog.observeAsState()

    Scaffold(
        backgroundColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.title_game_screen))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.deleteGameBacklog() }) {
                Icon(
                    painter = painterResource(id = R.drawable.trashcan),
                    contentDescription = "Delete"
                )
            }
        },
        content = { padding ->
            if (games.value?.isNotEmpty() == true) {
                ScreenContent(viewModel, games.value!!, padding)
            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
    )
}

@Composable
private fun ScreenContent(viewModel: GameViewModel, games: List<Game>, paddingValues: PaddingValues) {


    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        items(
            items = games,
            key = { item: Game -> item.id },
            itemContent = { item: Game -> ResultCard(game = item) }
        )
    }
}

@Composable
private fun ResultCard(game: Game) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = BottomNavigationDefaults.Elevation),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (game.result == "lose") {
            Text(
                text = stringResource(id = R.string.computer_wins),
                style = MaterialTheme.typography.h4
            )
        }

        if (game.result == "draw") {
            Text(text = stringResource(id = R.string.draw), style = MaterialTheme.typography.h4)
        }

        if (game.result == "win") {
            Text(
                text = stringResource(id = R.string.player_wins),
                style = MaterialTheme.typography.h4
            )
        }

        Text(
            text = game.date.toString(),
            style = MaterialTheme.typography.subtitle1
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = game.computerMove.img),
                    contentDescription = game.computerMove.name,
                    modifier = Modifier.background(Color.Blue)
                )
                Text(text = stringResource(id = R.string.computer))
            }

            Text(text = stringResource(id = R.string.vs), style = MaterialTheme.typography.h4)

            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = game.playerMove.img),
                    contentDescription = game.computerMove.name,
                    modifier = Modifier.background(Color.Blue)
                )
                Text(text = stringResource(id = R.string.you))
            }
        }

        Divider()
    }

}

@Composable
private fun BottomNavigationBar(
    navController: NavController,
) {
    BottomNavigation(
        backgroundColor = Color.White,
        content = {
            BottomNavigationItem(

                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.gamepad),
                        contentDescription = stringResource(
                            id = R.string.nav_play
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.nav_play),
                        fontSize = 15.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    navController.navigate(Screen.GameScreen.route)
                },
            )
            BottomNavigationItem(

                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.history),
                        contentDescription = stringResource(
                            id = R.string.nav_history
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.nav_history),
                        fontSize = 15.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = true,
                onClick = {
                    navController.navigate(Screen.HistoryScreen.route)
                },
            )
        })
}