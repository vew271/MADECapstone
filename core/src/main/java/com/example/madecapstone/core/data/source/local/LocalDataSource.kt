package com.example.madecapstone.core.data.source.local

import com.example.madecapstone.core.data.source.local.entity.FilmEntity
import com.example.madecapstone.core.data.source.local.room.FilmDAO
import com.example.madecapstone.core.utils.SortUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val filmDAO: FilmDAO) {

    fun getAllMovies(): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryMovies()
        return filmDAO.getMovies(query)
    }

    fun getMovieSearch(search: String): Flow<List<FilmEntity>> {
        return filmDAO.getSearchMovies(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    fun getAllFavoriteMovies(): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies()
        return filmDAO.getFavoriteMovies(query)
    }

    fun getAllTvShows(): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryTvShows()
        return filmDAO.getTvShows(query)
    }

    fun getTvShowSearch(search: String): Flow<List<FilmEntity>> {
        return filmDAO.getSearchTvShows(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    fun getAllFavoriteTvShows(): Flow<List<FilmEntity>> {
        val query = SortUtils.getSortedQueryFavoriteTvShows()
        return filmDAO.getFavoriteTvShows(query)
    }

    suspend fun insertMovies(movies: List<FilmEntity>) = filmDAO.insertMovie(movies)

    fun setMovieFavorite(movie: FilmEntity, newState: Boolean) {
        movie.favorite = newState
        filmDAO.updateFavoriteMovie(movie)
    }
}