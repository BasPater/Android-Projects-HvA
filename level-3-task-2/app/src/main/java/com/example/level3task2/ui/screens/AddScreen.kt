package com.example.level3task2.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.level3task2.Portal
import com.example.level3task2.R
import com.example.level3task2.viewmodel.PortalViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PortalAddScreen(
    navController: NavHostController,
    viewModel: PortalViewModel,
    modifier: Modifier,
    context: Context
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        content = {
            AddScreen(viewModel, modifier, context, navController)
        }
    )
}

@Composable
fun AddScreen(
    viewModel: PortalViewModel,
    modifier: Modifier,
    context: Context,
    navController: NavHostController
) {
    var newPortalName by remember { mutableStateOf(String()) }
    var newPortalURL by remember { mutableStateOf("https://") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text(text = stringResource(id = R.string.new_name)) },
            value = newPortalName,
            onValueChange = {
                newPortalName = it
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            label = { Text(text = stringResource(id = R.string.new_url)) },
            value = newPortalURL,
            onValueChange = {
                newPortalURL = it
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = {
                if (addNewPortal(viewModel, newPortalName, newPortalURL, context)) {
                    navController.navigate("${PortalScreens.ListScreen.name}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.add_portal))
        }
    }
}

fun addNewPortal(
    viewModel: PortalViewModel,
    newPortalName: String,
    newPortalURL: String,
    context: Context
): Boolean {
    if (newPortalName.isNotBlank() && URLUtil.isValidUrl(newPortalURL)) {
        viewModel.portals.add(Portal(newPortalName, newPortalURL))
        informUser(context, R.string.reminder_added)
        return true
    } else {
        informUser(context, R.string.not_empty)
        return false
    }
}

private fun informUser(context: Context, msgId: Int) {
    Toast.makeText(context, context.getString(msgId), Toast.LENGTH_LONG).show()
}
