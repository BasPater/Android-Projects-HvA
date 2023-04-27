package com.example.level6task2.data.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("page")val page: Int,
    @SerializedName("results")val list: List<Movie>,
    @SerializedName("total_pages")val total_pages: Int,
    @SerializedName("total_results")val total_results: Int
)