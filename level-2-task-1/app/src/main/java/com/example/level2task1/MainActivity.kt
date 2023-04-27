package com.example.level2task1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level2task1.ui.theme.Level2Task1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Level2Task1Theme {
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        content = { innerPadding -> ScreenContent(Modifier.padding(innerPadding)) }
    )
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    val context = LocalContext.current;

    // ArrayList to hold the predefined quiz statements. Also, initialize it.
    val quizStatements: MutableList<Statement> = remember { mutableStateListOf() }
    quizStatements.addAll(generateStatements())

    Column {
        Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            QuizInstructionsHeader()
        }
        AddQuizStatement(addStatement = { statement ->
            if (statement.statement.isNotBlank()) {
                quizStatements.add(statement)
            } else {
                informUser(context, R.string.not_empty)
            }
        })
        QuizStatements(
            quizStatements = quizStatements,
            removeQuizStatement = { statement: Statement ->
                quizStatements.remove(statement)
            },
        )
    }
}

@Composable
private fun QuizInstructionsHeader() {
    Column() {
        Text(
            text = stringResource(R.string.quiz_instr_header),
            fontSize = 30.sp
        )
        Text(text = stringResource(id = R.string.quiz_instr_description))
    }
}

@Composable
private fun AddQuizStatement(
    addStatement: (Statement) -> Unit
) {
    var statementText by remember { mutableStateOf(String()) }

Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = statementText,
        onValueChange = {
            statementText = it
        },
        placeholder = { Text(text = stringResource(R.string.new_quiz_statement))},

        )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {


        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff4caf50)),
            onClick = {
                addStatement(Statement(statementText, true))
                statementText = ""
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.width(100.dp),
        ) {
            Text(text = stringResource(id = R.string.button_true))
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0000)),
            onClick = {
                addStatement(Statement(statementText, false))
                statementText = ""
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.width(100.dp),
        ) {
            Text(text = stringResource(id = R.string.button_false))
        }
    }
}
}


@Composable
private fun QuizStatements(
    quizStatements: MutableList<Statement>,
    removeQuizStatement: (Statement) -> Unit
) {
    val context = LocalContext.current;

    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = quizStatements,
            key = {key -> key.statementID},
            itemContent = { quizStatement ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (quizStatement.isTrue) {
                                informUser(context, R.string.answer_is_true)
                                removeQuizStatement(quizStatement)
                            } else {
                                informUser(context, R.string.wrong_answer)
                            }
                        },
                        onLongPress = {
                            if (!quizStatement.isTrue) {
                                informUser(context, R.string.answer_is_false)
                                removeQuizStatement(quizStatement)
                            } else {
                                informUser(context, R.string.wrong_answer)
                            }
                        }
                    )
                }

            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = quizStatement.statement,
                )
            }
            Divider(
                color = Color.LightGray, modifier = Modifier.alpha(0.6f),
                thickness = 1.dp
            )
        })
    }

}


private fun generateStatements(): ArrayList<Statement> {
    return arrayListOf(
        Statement("A \'val\' and \'var\' are the same.", false),
        Statement("Mobile Application Development grants 12 ECTS.", false),
        Statement("A unit in Kotlin corresponds to a void in Java.", true),
        Statement("In Kotlin \'when\' replaces the \'switch\' operator in Java.", true)
    )
}

private fun informUser(context: Context, msgId: Int) {
    Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Level2Task1Theme {
        QuestionScreen()
    }
}