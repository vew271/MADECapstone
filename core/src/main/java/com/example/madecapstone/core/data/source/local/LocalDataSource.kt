package com.example.madecapstone.core.data.source.local

import com.example.madecapstone.core.data.source.local.entity.FilmEntity
import com.example.madecapstone.core.data.source.local.room.FilmDAO
import com.example.madecapstone.core.utils.SortUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val filmDAO: FilmDAO) {

    fun getAllMovies(sort: String): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryMovies(sort)
        return filmDAO.getMovies(query)
    }

    fun getAllTvShows(sort: String): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryTvShows(sort)
        return filmDAO.getTvShows(query)
    }

    fun getAllFavoriteMovies(sort: String): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies(sort)
        return filmDAO.getFavoriteMovies(query)
    }

    fun getAllFavoriteTvShows(sort: String): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryFavoriteTvShows(sort)
        return filmDAO.getFavoriteTvShows(query)
    }

    fun getMovieSearch(search: String): Flow<List<FilmEntity>> {
        return filmDAO.getSearchMovies(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    fun getTvShowSearch(search: String): Flow<List<FilmEntity>> {
        return filmDAO.getSearchTvShows(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    suspend fun insertMovies(movies: List<FilmEntity>) = filmDAO.insertMovie(movies)

    fun setMovieFavorite(movie: FilmEntity, newState: Boolean) {
        movie.favorite = newState
        filmDAO.updateFavoriteMovie(movie)
    }
}