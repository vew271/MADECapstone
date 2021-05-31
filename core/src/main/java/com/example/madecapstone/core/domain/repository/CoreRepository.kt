package com.example.madecapstone.core.domain.repository

import com.example.madecapstone.core.data.Resource
import com.example.madecapstone.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface CoreRepository {
    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getSearchMovies(search: String): Flow<List<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getAllTvShows(): Flow<Resource<List<Movie>>>

    fun getSearchTvShows(search: String): Flow<List<Movie>>

    fun getFavoriteTvShows(): Flow<List<Movie>>

    fun setMovieFavorite(movie: Movie, state: Boolean)
}