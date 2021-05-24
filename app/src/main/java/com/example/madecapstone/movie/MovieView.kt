package com.example.madecapstone.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.madecapstone.core.data.Resource
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.usecase.MovieUsecase

class MovieView(private val movieUsecase: MovieUsecase) : ViewModel() {
    fun getMovies(sort: String): LiveData<Resource<List<Movie>>> =
        movieUsecase.getAllMovies(sort).asLiveData()
}