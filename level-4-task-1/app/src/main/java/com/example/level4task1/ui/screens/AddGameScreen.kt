package com.example.level4task1.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level4task1.R
import com.example.level4task1.Utils
import com.example.level4task1.model.Game
import com.example.level4task1.model.GameViewModel
import java.time.LocalDate
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddGameScreen(navController: NavController, viewModel: GameViewModel) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    Scaffold(
        backgroundColor = Color.DarkGray,
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Game") },
                // "Arrow back" implementation.
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to homescreen"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val newGame = createGame(context, title, platform, day, month, year)
                if (newGame.title.isNotEmpty()) {
                    viewModel.insertGame(newGame)
                    navController.popBackStack()
                }
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Card {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = "Title",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier.fillMaxWidth()
                    )
                    CustomTextField(
                        value = platform,
                        onValueChange = { platform = it },
                        label = "Platform",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomTextField(
                            value = day,
                            onValueChange = { if (day.length <= 2) day = it },
                            label = "Day",
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier.weight(0.33f)
                        )
                        CustomTextField(
                            value = month,
                            onValueChange = { if (month.length <= 2) month = it },
                            label = "Month",
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier.weight(0.33f)
                        )
                        CustomTextField(
                            value = year,
                            onValueChange = { if (year.length <= 4) year = it },
                            label = "Year",
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier.weight(0.33f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        singleLine = true,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}


private fun createGame(
    context: Context, title: String, platform: String,
    day: String, month: String, year: String
): Game {
    if (title.isEmpty()) {
        return Game("", "", Date())
    } else if (month.isEmpty()) {
        return Game("", "", Date())
    } else if (year.isEmpty() || year.length != 4) {
        return Game("", "", Date())
    }
    return Game(title, platform, Utils.dayMonthYearToDate(day, month, year)!!)
}