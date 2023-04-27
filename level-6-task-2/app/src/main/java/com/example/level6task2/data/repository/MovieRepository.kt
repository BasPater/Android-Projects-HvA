package com.example.level6task2.data.repository

import android.util.Log
import com.example.level6task2.data.api.Api
import com.example.level6task2.data.api.ApiService
import com.example.level6task2.data.api.util.Resource
import com.example.level6task2.data.model.Movie
import com.example.level6task2.data.model.MovieList
import kotlinx.coroutines.withTimeout

class MovieRepository {
    private val apiService: ApiService = Api.createApi()

    suspend fun getMoviesByName(name: String): Resource<MovieList> {
        println("boe")

        val response = try {
            withTimeout(5_000) {
                apiService.getMoviesByName(name)
            }
        } catch (e: Exception){
            Log.e("MovieRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occured")
        }

        return Resource.Success(response)
    }
}