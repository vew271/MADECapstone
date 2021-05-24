package com.example.madecapstone.favorite.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.usecase.MovieUsecase

class FavoriteView(private val movieUsecase: MovieUsecase) : ViewModel() {
    fun getFavoriteMovies(sort: String): LiveData<List<Movie>> =
        movieUsecase.getFavoriteMovies(sort).asLiveData()

    fun getFavoriteTvShows(sort: String): LiveData<List<Movie>> =
        movieUsecase.getFavoriteTvShows(sort).asLiveData()

    fun setFavorite(Movie: Movie, newState: Boolean) {
        movieUsecase.setMovieFavorite(Movie, newState)
    }
}