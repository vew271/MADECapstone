package com.example.madecapstone.favorite.di

import com.example.madecapstone.favorite.favorite.FavoriteView
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel {
        FavoriteView(get())
    }
}