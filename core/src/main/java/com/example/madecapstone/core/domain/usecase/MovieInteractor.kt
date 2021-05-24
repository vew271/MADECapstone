package com.example.madecapstone.core.domain.usecase

import com.example.madecapstone.core.data.Resource
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val coreRepository: CoreRepository) : MovieUsecase {
    override fun getAllMovies(sort: String): Flow<Resource<List<Movie>>> =
        coreRepository.getAllMovies(sort)

    override fun getAllTvShows(sort: String): Flow<Resource<List<Movie>>> =
        coreRepository.getAllTvShows(sort)

    override fun getFavoriteMovies(sort: String): Flow<List<Movie>> =
        coreRepository.getFavoriteMovies(sort)

    override fun getSearchMovies(search: String): Flow<List<Movie>> =
        coreRepository.getSearchMovies(search)

    override fun getSearchTvShows(search: String): Flow<List<Movie>> =
        coreRepository.getSearchTvShows(search)

    override fun getFavoriteTvShows(sort: String): Flow<List<Movie>> =
        coreRepository.getFavoriteTvShows(sort)

    override fun setMovieFavorite(movie: Movie, state: Boolean) =
        coreRepository.setMovieFavorite(movie, state)
}