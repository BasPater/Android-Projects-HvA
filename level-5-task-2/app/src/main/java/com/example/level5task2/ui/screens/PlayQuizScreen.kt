package com.example.level5task2.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level5task2.R
import com.example.level5task2.viewModel.QuizViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlayQuizScreen(navController: NavController, viewModel: QuizViewModel) {
    val context = LocalContext.current
    viewModel.getQuestions()
    val questions by viewModel.questions.observeAsState()
    var iterator by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }


    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.app_name))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        },
        content = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(
                        id = R.string.number_of_questions,
                        iterator + 1,
                        questions?.size ?: 0
                    )

                )
            }

            Column(

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                if (!questions.isNullOrEmpty()) {
                    Text(text = questions?.get(iterator)!!.question)


                    questions?.get(iterator)!!.choices.forEach { text ->

                        Row(
                            modifier = Modifier

                                .fillMaxWidth()
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        selectedOption = text
                                        println(selectedOption)
                                    }
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            RadioButton(
                                selected = (selectedOption == text),
                                modifier = Modifier.padding(all = Dp(value = 8F)),
                                onClick = {
                                    selectedOption = text
                                    println(selectedOption)
                                }
                            )
                            Text(
                                text = text,
                            )
                        }
                    }

                    Button(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (selectedOption == questions?.get(iterator)!!.correctAnswer) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.correct_answer),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.incorrect_answer),
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if (questions?.size == iterator + 1) {
                                navController.navigate(Screens.HomeScreen.route)
                            } else {
                                iterator++
                            }
                        }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
            }

        },
    )
}
