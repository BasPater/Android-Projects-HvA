package com.example.level3task2.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.level3task2.Portal
import com.example.level3task2.R
import com.example.level3task2.viewmodel.PortalViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PortalListScreen(
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
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Listview(viewModel, modifier, context)
            }
        },
        floatingActionButton = { ListScreenLab(navController) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Listview(viewModel: PortalViewModel, modifier: Modifier, context: Context) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        content = {
            items(viewModel.portals.size) { index ->
                Card(
                    backgroundColor = Color.LightGray,
                    onClick = {
                        openTab(context, viewModel.portals[index])
                    },
                    modifier = Modifier.padding(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = viewModel.portals[index].name,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 12.dp),
                            text = viewModel.portals[index].url,
                        )
                    }
                }

            }
        })
}


@Composable
fun ListScreenLab(navController: NavHostController) {
    FloatingActionButton(onClick = {
        navController.navigate("${PortalScreens.AddScreen.name}")
    }) {
        Icon(Icons.Filled.Add, "")
    }
}

fun openTab(context: Context, portal: Portal) {
    // on below line we are creating a variable for
    // package name and specifying package name as
    // package of chrome application.
    val package_name = "com.android.chrome"

    // on below line we are creating a variable
    // for the activity and initializing it.
    val activity = (context as? Activity)

    // on below line we are creating a variable for
    // our builder and initializing it with
    // custom tabs intent
    val builder = CustomTabsIntent.Builder()

    // on below line we are setting show title
    // to true to display the title for
    // our chrome tabs.
    builder.setShowTitle(true)

    // on below line we are enabling instant
    // app to open if it is available.
    builder.setInstantAppsEnabled(true)
    // on below line we are setting tool bar color for our custom chrome tabs.
    builder.setToolbarColor(ContextCompat.getColor(context, R.color.purple_700))

    // on below line we are creating a
    // variable to build our builder.
    val customBuilder = builder.build()

    // on below line we are checking if the package name is null or not.
    if (package_name != null) {
        // on below line if package name is not null
        // we are setting package name for our intent.
        customBuilder.intent.setPackage(package_name)

        // on below line we are calling launch url method
        // and passing url to it on below line.
        customBuilder.launchUrl(context, Uri.parse(portal.url))
    } else {
        // this method will be called if the
        // chrome is not present in user device.
        // in this case we are simply passing URL
        // within intent to open it.
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(portal.url))

        // on below line we are calling start
        // activity to start the activity.
        activity?.startActivity(i)
    }

}