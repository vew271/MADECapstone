package com.example.madecapstone.core.data.source.remote

import com.example.madecapstone.core.data.source.remote.network.ApiResponse
import com.example.madecapstone.core.data.source.remote.network.RetrofitAPI
import com.example.madecapstone.core.data.source.remote.response.MovieResponse
import com.example.madecapstone.core.data.source.remote.response.TvResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val retrofitAPI: RetrofitAPI) {
    private val apiKey = "4fabea41a5506693009cdead536f1965"

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = retrofitAPI.getMovies(apiKey)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvResponse>>> {
        return flow {
            try {
                val response = retrofitAPI.getTvShows(apiKey)
                val tvShowList = response.results
                if (tvShowList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}