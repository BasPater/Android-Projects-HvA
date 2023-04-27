package com.example.level2task1

import java.util.*


data class Statement(
    val statement: String,
    val isTrue: Boolean,
    val statementID: UUID = UUID.randomUUID()
)