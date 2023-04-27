package com.example.level3task1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.level3task1.R
import com.example.level3task1.viewmodel.GameViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SummaryScreen(
    navController: NavHostController,
    viewModel: GameViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        content = {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                SummaryScreenContent(navController = navController, viewModel = viewModel)
            }
        }
    )
}

@Composable
fun SummaryScreenContent(navController: NavHostController, viewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.rate_result, viewModel.randomlyChosenGame.value, viewModel.gameRatingAccordingToUser.value),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFAAAAAA)),
            onClick = {
                viewModel.randomAssessableGame()
                navController.popBackStack("${GameRatingScreens.StartScreen.name}", false)
            }
        ) {
            Text(text = stringResource(id = R.string.start_over))
        }
    }
}