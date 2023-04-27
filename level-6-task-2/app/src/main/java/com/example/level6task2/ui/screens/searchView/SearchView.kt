package com.example.level6task2.ui.screens.searchView

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.level6task2.R
import com.example.level6task2.data.api.util.Resource
import com.example.level6task2.data.model.Movie
import com.example.level6task2.data.model.MovieList
import com.example.level6task2.ui.screens.Screens

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchView(viewModel: MoviesViewModel, navController: NavController) {
    val searchQueryState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val moviesResource: Resource<MovieList>? by viewModel.movieResource.observeAsState()



    Column {

        TextField(
            keyboardActions = KeyboardActions(onDone = {
                viewModel.getMoviesByName(searchQueryState.value.text)
                //based on @ExperimentalComposeUiApi - if this doesn't work in a newer version remove it
                //no alternative in compose for hiding keyboard at time of writing
                keyboardController?.hide()
            }),
            value = searchQueryState.value,
            onValueChange = { value ->
                searchQueryState.value = value

            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            leadingIcon = {
                IconButton(onClick = {
                    viewModel.getMoviesByName(searchQueryState.value.text)
                    //based on @ExperimentalComposeUiApi - if this doesn't work in a newer version remove it
                    //no alternative in compose for hiding keyboard at time of writing
                    keyboardController?.hide()
                }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                    )
                }
            },
            trailingIcon = {
                if (searchQueryState.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            viewModel.getMoviesByName(searchQueryState.value.text)
                            searchQueryState.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                            keyboardController?.show()
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(16.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_movie_hint),
                    color = Color.White
                )
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                leadingIconColor = Color.White,
                trailingIconColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            when (moviesResource) {
                is Resource.Success -> {
                    if ((moviesResource?.data?.list?.size ?: 0) == 0) {
                        Text(text = stringResource(id = R.string.no_result))
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 120.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.fillMaxSize()
                            ) {
                            items(moviesResource?.data?.list?.size ?: 0) { index ->

                                Card(
                                    shape = RectangleShape,
                                    onClick = {
                                        viewModel.selectedMovie = moviesResource?.data?.list?.get(index)
                                        navController.navigate(Screens.MovieDetailScreen.route)
                                    },
                                    modifier = Modifier
                                        .height(170.dp)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            "https://image.tmdb.org/t/p/original" + moviesResource?.data?.list?.get(
                                                index
                                            )?.poster_path
                                        ),
                                        contentDescription = moviesResource?.data?.list?.get(
                                            index)?.title,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop

                                    )
                                }

                            }
                        }
                    }


                }
                is Resource.Error -> {
                    Text(text = moviesResource?.message.toString())
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp),
                        color = MaterialTheme.colors.primary,
                        strokeWidth = 10.dp
                    )
                }
                else -> {
                    Text(text = stringResource(id = R.string.search_for_movie))
                }
            }
        }

    }
}

