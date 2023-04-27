package com.example.level2task2

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level2task2.ui.theme.Level2Task2Theme
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Level2Task2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    QuestionScreen()
                }
            }
        }
    }
}

@Composable
private fun QuestionScreen() {
    var equation by remember { mutableStateOf(Equation("-", "-", "?")) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        content = { innerPadding ->
            ScreenContent(Modifier.padding(innerPadding), equation, changeEquation = {
                equation = it
            }
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = stringResource(id = R.string.next)) },
                onClick = {
                    equation = randomEquation()
                }
            )
        }
    )
}

@Composable
fun ScreenContent(modifier: Modifier, equation: Equation, changeEquation: (Equation) -> (Unit)) {
    var context = LocalContext.current;

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = stringResource(id = R.string.title_page),
            fontSize = 25.sp,
        )
        TableTop(modifier)

        Statements(modifier, equation)

        if (!(equation.answer.equals("?") || equation.answer.equals("✅"))) {
            AnswerButtons(modifier, checkAnswer = { boolean ->
                if (equation.firstArg.equals(equation.secondArg) && equation.answer.equals("T") && boolean) {
                    informUser(context, R.string.correct_answer)
                    changeEquation(correctAnswer())
                    return@AnswerButtons
                }
                if (!equation.firstArg.equals(equation.secondArg) && equation.answer.equals("F") && boolean) {
                    informUser(context, R.string.correct_answer)
                    changeEquation(correctAnswer())
                    return@AnswerButtons
                }

                if (equation.firstArg.equals(equation.secondArg) && equation.answer.equals("F") && !boolean) {
                    informUser(context, R.string.correct_answer)
                    changeEquation(correctAnswer())
                    return@AnswerButtons
                }

                if (!equation.firstArg.equals(equation.secondArg) && equation.answer.equals("T") && !boolean) {
                    informUser(context, R.string.correct_answer)
                    changeEquation(correctAnswer())
                    return@AnswerButtons
                }
                informUser(context, R.string.incorrect_answer)


            })
        }

    }
}

@Composable
private fun TableTop(modifier: Modifier) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(id = R.string.a),
            fontSize = 25.sp
        )
        Text(
            text = stringResource(id = R.string.b),
            fontSize = 25.sp
        )
        Text(
            text = stringResource(id = R.string.a_and_b),
            fontSize = 25.sp
        )
    }
}

@Composable
private fun Statements(modifier: Modifier, equation: Equation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = equation.firstArg,
            fontSize = 25.sp,
            color = Color.LightGray
        )
        Text(
            text = equation.secondArg,
            fontSize = 25.sp,
            color = Color.LightGray
        )
        Text(
            text = equation.answer,
            fontSize = 25.sp,
            color = Color.LightGray
        )
    }
}

@Composable
private fun AnswerButtons(
    modifier: Modifier,
    checkAnswer: (Boolean) -> (Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff4caf50)),
            onClick = {
                checkAnswer(true)
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .width(100.dp),
        ) {
            Text(text = stringResource(id = R.string.button_true))
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0000)),
            onClick = {
                checkAnswer(false)
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.width(100.dp),
        ) {
            Text(text = stringResource(id = R.string.button_false))
        }
    }
}

private fun informUser(context: Context, msgId: Int) {
    Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show()
}

private fun randomEquation(): Equation {
    var randomInt: Int = (0..1).random()
    var firstArg: String = "F"
    var secondArg: String = "F"
    var answer: String = "F"

    if (randomInt == 0) {
        firstArg = "T"
    }
    randomInt = (0..1).random()

    if (randomInt == 0) {
        secondArg = "T"
    }

    randomInt = (0..1).random()

    if (randomInt == 0) {
        answer = "T"
    }

    return Equation(firstArg, secondArg, answer)
}

private fun correctAnswer(): Equation {
    return Equation("✅", "✅", "✅")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Level2Task2Theme {
        QuestionScreen()
    }
}