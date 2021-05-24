package com.example.madecapstone.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.madecapstone.core.domain.usecase.MovieUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchView(private val movieUsecase: MovieUsecase) : ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        queryChannel.offer(search)
    }

    val movieResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieUsecase.getSearchMovies(it)
        }.asLiveData()

    val tvShowResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieUsecase.getSearchTvShows(it)
        }.asLiveData()
}