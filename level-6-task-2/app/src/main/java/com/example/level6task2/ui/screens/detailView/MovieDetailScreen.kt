package com.example.level6task2.ui.screens.detailView

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.level6task2.R
import com.example.level6task2.data.api.util.Resource
import com.example.level6task2.data.model.MovieList
import com.example.level6task2.ui.screens.searchView.MoviesViewModel
import kotlin.math.round

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(viewModel: MoviesViewModel, navController: NavController) {
    val moviesResource = viewModel.selectedMovie


    Scaffold(
        content = {

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/original" + moviesResource?.backdrop_path,
                    contentDescription = null
                )

                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/original" + moviesResource?.poster_path,
                        contentDescription = null,
                        modifier = Modifier
                            .width(120.dp)
                            .clip(RoundedCornerShape(10.dp)),

                        )

                    Column(
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        Text(
                            text = moviesResource?.title.toString(),
                            fontSize = 25.sp
                        )

                        Row(
                            modifier = Modifier.padding(top = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(id = R.string.star)
                            )

                            Text(
                                text = "%.1f".format(moviesResource?.vote_average)
                            )
                        }


                    }


                }

                Text(
                    text = stringResource(id = R.string.overview),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 15.dp)
                )

                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 15.dp),
                    text = moviesResource?.overview.toString()
                )

            }


        }

    )

}