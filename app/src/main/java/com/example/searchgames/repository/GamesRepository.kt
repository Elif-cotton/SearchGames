package com.example.searchgames.repository

import com.example.searchgames.data.ApiGames
import com.example.searchgames.model.GameList
import com.example.searchgames.model.SingleGameModel
import javax.inject.Inject

class GamesRepository @Inject constructor(private val apiGames: ApiGames) {

    suspend fun getGames(): List<GameList>? {
        val response = apiGames.getGames()
        if (response.isSuccessful){
            return response.body()?.results
        }
        return null
    }

    suspend fun getGameById(id: Int): SingleGameModel? {
        val response = apiGames.getGameById(id)
        if(response.isSuccessful){
            return response.body()
        }
        return null
    }

    suspend fun getGameByName(name: String): SingleGameModel? {
        val response = apiGames.getGameByName(name)
        if(response.isSuccessful){
            return response.body()
        }
        return null
    }


}