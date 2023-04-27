package com.example.level6task1.ui.screens.dogScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.level6task1.R
import com.example.level6task1.data.api.util.Resource
import com.example.level6task1.data.model.Cat
import com.example.level6task1.data.model.Dog
import com.example.level6task1.ui.screens.catsScreen.CatsViewModel

@Composable
fun DogsScreen(
    viewModel: DogsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val context = LocalContext.current

    val dogResource: Resource<Dog>? by viewModel.dogsResource.observeAsState()


    var dog= when (dogResource) {
        is Resource.Success -> dogResource?.data?.message
        is Resource.Error -> dogResource?.message
        is Resource.Loading -> stringResource(id = R.string.loading)
        is Resource.Empty -> stringResource(id = R.string.click)
        else -> stringResource(id = R.string.something_went_wrong)
    }

//    val breed = dogResource?.data?.message?.

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.title_dog),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp)
                .heightIn(min = 96.dp)
        )

        Text(
            text = dog?.substringBeforeLast("/")?.substringAfterLast("/")?: dog.toString(),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp)
                .heightIn(min = 96.dp)
        )
        Image(
            painter = rememberAsyncImagePainter(dog),
            contentDescription = "dog",
            modifier = Modifier.size(250.dp)
        )


        //heightIn = min height for varying text length

        ExtendedFloatingActionButton(
            text = { Text(text = stringResource(R.string.get_new_dog)) },
            onClick = { viewModel.getDog() },
            icon = { Icon(Icons.Filled.Refresh, "") },
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )


    }
}