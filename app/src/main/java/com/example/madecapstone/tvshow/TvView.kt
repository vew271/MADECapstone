package com.example.madecapstone.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.madecapstone.core.data.Resource
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.usecase.MovieUsecase

class TvView(private val movieUsecase: MovieUsecase) : ViewModel() {
    fun getTvShows(sort: String): LiveData<Resource<List<Movie>>> =
        movieUsecase.getAllTvShows(sort).asLiveData()
}