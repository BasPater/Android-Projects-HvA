package com.example.level4task2.repository

import android.content.Context
import com.example.level4task2.database.GameDao
import com.example.level4task2.database.GameDatabase
import com.example.level4task2.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun insert(game: Game) = gameDao.insert(game)

    suspend fun delete(game: Game) = gameDao.delete(game)

    fun getGames() = gameDao.getGames()

    suspend fun deleteAll() = gameDao.deleteAll()

     fun countWins() = gameDao.countWins()
     fun countDraws() = gameDao.countDraws()
     fun countLoses() = gameDao.countLoses()

}