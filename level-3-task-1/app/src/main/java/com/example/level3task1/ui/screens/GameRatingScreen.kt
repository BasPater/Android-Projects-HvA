package com.example.level3task1.ui.screens

import android.annotation.SuppressLint
import android.util.Log
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
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RatingScreen(
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
                RatingBarContainer(navController, viewModel)
            }
        }
    )
}

@Composable
fun RatingBarContainer(navController: NavHostController, viewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.rate_game),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = viewModel.randomlyChosenGame.value
        )
        RatingBar(
            modifier = Modifier
                .padding(16.dp),
            value = viewModel.gameRatingAccordingToUser.value,
            onValueChange = {
                viewModel.gameRatingAccordingToUser.value = it
            },
            onRatingChanged = {
                Log.d("TAG", "onRatingChanged: $it")
            },
            config = RatingBarConfig()
                .stepSize(StepSize.HALF) // Also allow half star ratings.
                .style(RatingBarStyle.HighLighted),
        )

        Button(
            onClick = {
                navController.navigate("${GameRatingScreens.SummaryScreen.name}")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFAAAAAA)),

        ) {
            Text(text = stringResource(id = R.string.to_summary))
        }
    }

}
