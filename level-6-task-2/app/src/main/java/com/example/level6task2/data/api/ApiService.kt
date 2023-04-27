package com.example.level6task2.data.api


import com.example.level6task2.data.model.Movie
import com.example.level6task2.data.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie?api_key=${Api.API_KEY}")
    suspend fun getMoviesByName(@Query("query") query: String): MovieList

//    @GET("/api/breeds/image/random")
//    suspend fun getRandomDog(): Movie
}