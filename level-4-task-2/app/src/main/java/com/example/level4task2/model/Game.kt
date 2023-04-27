package com.example.level4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game")
data class Game(
    @ColumnInfo(name = "computerMove")
    var computerMove: Moves,

    @ColumnInfo(name = "playerMove")
    var playerMove: Moves,

    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "date")
    var date: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
)