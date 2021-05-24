package com.example.madecapstone.core.utils

import com.example.madecapstone.core.data.source.local.entity.FilmEntity
import com.example.madecapstone.core.data.source.remote.response.MovieResponse
import com.example.madecapstone.core.data.source.remote.response.TvResponse
import com.example.madecapstone.core.domain.model.Movie

object Mapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<FilmEntity> {
        val movieList = ArrayList<FilmEntity>()
        input.map {
            val movie = FilmEntity(
                it.id,
                it.title,
                it.releaseDate,
                it.overview,
                it.popularity,
                it.voteAverage,
                it.voteCount,
                it.posterPath,
                favorite = false,
                isTvShows = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapTvShowResponsesToEntities(input: List<TvResponse>): List<FilmEntity> {
        val movieList = ArrayList<FilmEntity>()
        input.map {
            val movie = FilmEntity(
                it.id,
                it.name,
                it.firstAirDate,
                it.overview,
                it.popularity,
                it.voteAverage,
                it.voteCount,
                it.posterPath,
                favorite = false,
                isTvShows = true
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<FilmEntity>): List<Movie> {
        return input.map {
            Movie(
                it.id,
                it.title,
                it.releaseDate,
                it.overview,
                it.popularity,
                it.voteAverage,
                it.voteCount,
                it.posterPath,
                favorite = it.favorite,
                isTvShows = it.isTvShows
            )
        }
    }

    fun mapDomainToEntity(input: Movie): FilmEntity {
        return FilmEntity(
            input.id,
            input.title,
            input.releaseDate,
            input.overview,
            input.popularity,
            input.voteAverage,
            input.voteCount,
            input.posterPath,
            favorite = input.favorite,
            isTvShows = input.isTvShows
        )
    }
}