package com.example.searchgames.data

import com.example.searchgames.util.Constants.Companion.API_KEY
import com.example.searchgames.util.Constants.Companion.ENDPOINT
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGames {

    @GET(ENDPOINT + API_KEY )
    suspend fun getGames(): Response<GamesModel>

    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id")id : Int): Response<SingleGameModel>
}