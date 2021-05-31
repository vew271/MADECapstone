package com.example.madecapstone.favorite.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.usecase.MovieUsecase

class FavoriteView(private val movieUsecase: MovieUsecase) : ViewModel() {
    fun getFavoriteMovies(): LiveData<List<Movie>> =
        movieUsecase.getFavoriteMovies().asLiveData()

    fun getFavoriteTvShows(): LiveData<List<Movie>> =
        movieUsecase.getFavoriteTvShows().asLiveData()

    fun setFavorite(Movie: Movie, newState: Boolean) {
        movieUsecase.setMovieFavorite(Movie, newState)
    }
}