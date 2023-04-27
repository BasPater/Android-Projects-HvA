package com.example.level6task1.data.repository

import android.util.Log
import com.example.level6task1.data.api.Api
import com.example.level6task1.data.api.ApiService
import com.example.level6task1.data.api.util.Resource
import com.example.level6task1.data.model.Cat
import kotlinx.coroutines.withTimeout

class CatsRepository {
    private val apiService: ApiService = Api.catsClient

    suspend fun getRandomCat() : Resource<Cat> {
        val response = try {
            withTimeout(5_000) {
                apiService.getRandomCat()
            }
        } catch (e: Exception) {
            Log.e("CatsRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occured")
        }

        return Resource.Success(response)
    }
}