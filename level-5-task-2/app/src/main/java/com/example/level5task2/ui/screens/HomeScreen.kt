@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.level5task2.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.level5task2.R
import com.example.level5task2.viewModel.QuizViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: QuizViewModel) {

    val context = LocalContext.current



    androidx.compose.material.Scaffold(

        topBar = {
            androidx.compose.material.TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material.Text(text = stringResource(id = R.string.app_name))
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = context.getString(R.string.welcom_title),
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Text(
                text = context.getString(R.string.welcome_text),
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Screens.PlayQuizScreen.route)
                }) {
                Text(
                    text = context.getString(R.string.welcom_title),
                    color = Color.White
                )

            }
        }

    }
}