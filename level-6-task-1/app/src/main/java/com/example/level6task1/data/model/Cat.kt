package com.example.level6task1.data.model

import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("createdAt") val creationDate: String,
    @SerializedName("url") val urlExtension: String
)
