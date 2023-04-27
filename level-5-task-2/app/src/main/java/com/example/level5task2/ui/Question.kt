package com.example.level5task2.ui

data class Question (
    val choices: ArrayList<String>,
    val correctAnswer: String,
    val id: Long,
    val question: String
        )
