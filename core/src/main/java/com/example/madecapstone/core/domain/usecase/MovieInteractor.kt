package com.example.madecapstone.core.domain.usecase

import com.example.madecapstone.core.data.Resource
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val coreRepository: CoreRepository) : MovieUsecase {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        coreRepository.getAllMovies()

    override fun getSearchMovies(search: String): Flow<List<Movie>> =
        coreRepository.getSearchMovies(search)

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        coreRepository.getFavoriteMovies()

    override fun getAllTvShows(): Flow<Resource<List<Movie>>> =
        coreRepository.getAllTvShows()

    override fun getSearchTvShows(search: String): Flow<List<Movie>> =
        coreRepository.getSearchTvShows(search)

    override fun getFavoriteTvShows(): Flow<List<Movie>> =
        coreRepository.getFavoriteTvShows()

    override fun setMovieFavorite(movie: Movie, state: Boolean) =
        coreRepository.setMovieFavorite(movie, state)
}