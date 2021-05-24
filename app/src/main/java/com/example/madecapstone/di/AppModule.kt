package com.example.madecapstone.di

import com.example.madecapstone.core.domain.usecase.MovieInteractor
import com.example.madecapstone.core.domain.usecase.MovieUsecase
import com.example.madecapstone.detail.DetailView
import com.example.madecapstone.main.SearchView
import com.example.madecapstone.movie.MovieView
import com.example.madecapstone.tvshow.TvView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUsecase> { MovieInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MovieView(get()) }
    viewModel { TvView(get()) }
    viewModel { DetailView(get()) }
    viewModel { SearchView(get()) }
}