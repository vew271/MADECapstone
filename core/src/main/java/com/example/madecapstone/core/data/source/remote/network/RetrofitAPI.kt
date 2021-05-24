package com.example.madecapstone.core.data.source.remote.network

import com.example.madecapstone.core.data.source.remote.response.ListMovie
import com.example.madecapstone.core.data.source.remote.response.ListTv
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
    ): ListMovie

    @GET("tv")
    suspend fun getTvShows(
        @Query("api_key") apiKey: String
    ): ListTv
}