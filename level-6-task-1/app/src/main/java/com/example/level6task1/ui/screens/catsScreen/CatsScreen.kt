package com.example.level6task1.ui.screens.catsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.level6task1.R
import com.example.level6task1.data.api.Api
import com.example.level6task1.data.api.util.Resource
import com.example.level6task1.data.model.Cat

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val catResource: Resource<Cat>? by viewModel.catsResource.observeAsState()

    val imageUrl = Api.CATS_BASE_URL +  catResource?.data?.urlExtension

    var cat = when (catResource) {
        is Resource.Success -> catResource?.data
        is Resource.Error -> catResource?.message
        is Resource.Loading -> stringResource(id = R.string.loading)
        is Resource.Empty -> stringResource(id = R.string.click_cat)
        else -> stringResource(id = R.string.something_went_wrong)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.title_cat),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp)
                .heightIn(min = 96.dp)
        )

        Text(
            text = catResource?.data?.creationDate ?: stringResource(id = R.string.click_cat),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
                .heightIn(min = 96.dp)
        )

        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "dog",
            modifier = Modifier.size(250.dp)
        )
        //heightIn = min height for varying text length

        ExtendedFloatingActionButton(
            text = { Text(text = stringResource(R.string.get_new_cat)) },
            onClick = { viewModel.getCat() },
            icon = { Icon(Icons.Filled.Refresh, "") },
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
    }


}