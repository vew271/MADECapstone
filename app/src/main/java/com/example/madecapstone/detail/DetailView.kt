package com.example.madecapstone.detail

import androidx.lifecycle.ViewModel
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.usecase.MovieUsecase

class DetailView(private val movieUsecase: MovieUsecase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) {
        movieUsecase.setMovieFavorite(movie, newStatus)
    }
}
