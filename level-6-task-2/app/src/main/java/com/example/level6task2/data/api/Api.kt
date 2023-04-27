package com.example.level6task2.data.api

import com.example.level6task2.data.repository.MovieRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {
        // The base url off the api.
        private const val baseUrl = "https://api.themoviedb.org/3/"

        const val API_KEY :String = "72bc3c9f0f8712e1180c821b4f5c370c"

        /**
         * @return [ApiService] The service class off the retrofit client.
         */
        fun createApi(): ApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val triviaApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit NumbersApiService
            return triviaApi.create(ApiService::class.java)
        }
    }
}