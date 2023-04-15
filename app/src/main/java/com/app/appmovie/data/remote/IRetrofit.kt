package com.app.appmovie.data.remote

import com.app.appmovie.models.MovieTopDbResult
import com.app.appmovie.models.MoviesResultNow
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {
    @GET("movie/top_rated")
    suspend fun listTopMovies(@Query("api_key")apiKey:String): MovieTopDbResult
    @GET("movie/now_playing")
    suspend fun listNowMovies(@Query("api_key")apiKey:String):MoviesResultNow
}