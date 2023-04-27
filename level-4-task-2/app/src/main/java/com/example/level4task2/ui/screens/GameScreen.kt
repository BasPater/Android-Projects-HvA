package com.example.level4task2.ui.screens

import android.annotation.SuppressLint
import android.view.Display.Mode
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.level4task2.R
import com.example.level4task2.model.Game
import com.example.level4task2.model.GameViewModel
import com.example.level4task2.model.Moves
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val context = LocalContext.current

    Scaffold(
        backgroundColor = Color.White,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
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
        }
    ) {
        ScreenContent(viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(viewModel: GameViewModel) {
    var games = viewModel.gameBacklog.observeAsState()
    var game by remember { mutableStateOf(Game(Moves.Paper, Moves.Scissors, "win", Date())) }
    var result by remember { mutableStateOf(R.string.choose_option) }
    var win = viewModel.win.observeAsState()
    var draw = viewModel.draw.observeAsState()
    var lose = viewModel.lose.observeAsState()
    var notFirstMove by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(top = 20.dp),
        )
        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(id = R.string.instructions),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(
                id = R.string.statistics_title,
                win.value.toString(),
                draw.value.toString(),
                lose.value.toString()
            ),
            style = MaterialTheme.typography.overline
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(
                id = R.string.statistics,
                win.value.toString(),
                draw.value.toString(),
                lose.value.toString()
            ),
            style = MaterialTheme.typography.overline
        )

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(id = result), style = MaterialTheme.typography.h4
        )
        if (notFirstMove) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = game.computerMove.img),
                        contentDescription = game.computerMove.name,
                        modifier = Modifier.background(Color.Blue)
                    )
                    Text(text = stringResource(id = R.string.computer))
                }

                Text(
                    text = stringResource(id = R.string.vs),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = game.playerMove.img),
                        contentDescription = game.computerMove.name,
                        modifier = Modifier.background(Color.Blue)
                    )
                    Text(text = stringResource(id = R.string.you))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                backgroundColor = Color.LightGray,
                onClick = {
                    game = calculateResult(Moves.Rock, changeResult = {
                        result = it
                        notFirstMove = true
                    })
                    viewModel.insertGame(game)
                }
            ) {
                Image(painter = painterResource(id = R.drawable.rock), contentDescription = "Rock")
            }

            Card(
                backgroundColor = Color.LightGray,
                onClick = {
                    game = calculateResult(Moves.Paper, changeResult = {
                        result = it
                        notFirstMove = true
                    })
                    viewModel.insertGame(game)
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paper),
                    contentDescription = "Paper"
                )
            }

            Card(
                backgroundColor = Color.LightGray,
                onClick = {
                    game = calculateResult(Moves.Scissors, changeResult = {
                        result = it
                        notFirstMove = true
                    })
                    viewModel.insertGame(game)
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.scissors),
                    contentDescription = "Scissors"
                )
            }
        }
    }
}


fun countWins(games: List<Game>): Int {
    games.stream().filter { game -> game.result == "win" }.count().or(0).toInt()
    return 0
}

private fun calculateResult(playerMove: Moves, changeResult: (Int) -> (Unit)): Game {
    val computerMove = Moves.values().toList().shuffled().first()

    if (computerMove == playerMove) {
        changeResult(R.string.draw)
        return Game(computerMove, playerMove, "draw", Date())
    }

    if (computerMove == Moves.Rock && playerMove == Moves.Scissors) {
        changeResult(R.string.computer_wins)
        return Game(computerMove, playerMove, "lose", Date())
    }

    if (computerMove == Moves.Paper && playerMove == Moves.Rock) {
        changeResult(R.string.computer_wins)
        return Game(computerMove, playerMove, "lose", Date())
    }

    if (computerMove == Moves.Scissors && playerMove == Moves.Paper) {
        changeResult(R.string.computer_wins)
        return Game(computerMove, playerMove, "lose", Date())
    }
    changeResult(R.string.player_wins)
    return Game(computerMove, playerMove, "win", Date())

}


@Composable
private fun BottomNavigationBar(
    navController: NavController,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,

        ) {
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
            selected = true,
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
            selected = false,
            onClick = {
                navController.navigate(Screen.HistoryScreen.route)
            },
        )
    }
}