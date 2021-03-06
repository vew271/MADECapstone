package com.example.madecapstone.core.utils

import com.example.madecapstone.core.data.source.local.entity.FilmEntity
import com.example.madecapstone.core.data.source.remote.response.MovieResponse
import com.example.madecapstone.core.data.source.remote.response.TvResponse
import com.example.madecapstone.core.domain.model.Movie

object Mapper {
    fun movieResponsesToEntities(input: List<MovieResponse>): List<FilmEntity> {
        val movieList = ArrayList<FilmEntity>()
        input.map {
            val movie = FilmEntity(
                it.id,
                it.title,
                it.releaseDate,
                it.overview,
                it.posterPath,
                it.backdropPath,
                favorite = false,
                isTvShows = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun tvResponsesToEntities(input: List<TvResponse>): List<FilmEntity> {
        val movieList = ArrayList<FilmEntity>()
        input.map {
            val movie = FilmEntity(
                it.id,
                it.name,
                it.firstAirDate,
                it.overview,
                it.posterPath,
                it.backdropPath,
                favorite = false,
                isTvShows = true
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun entityToDomain(input: List<FilmEntity>): List<Movie> {
        return input.map {
            Movie(
                it.id,
                it.title,
                it.releaseDate,
                it.overview,
                it.posterPath,
                it.backdropPath,
                favorite = it.favorite,
                isTvShows = it.isTvShows
            )
        }
    }

    fun domainToEntity(input: Movie): FilmEntity {
        return FilmEntity(
            input.id,
            input.title,
            input.releaseDate,
            input.overview,
            input.posterPath,
            input.backdropPath,
            favorite = input.favorite,
            isTvShows = input.isTvShows
        )
    }
}