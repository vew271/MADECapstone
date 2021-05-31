package com.example.madecapstone.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    fun getSortedQueryMovies(): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM filmEntity where isTvShow = 0 ORDER BY releaseDate DESC")
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryTvShows(): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM filmEntity where isTvShow = 1 ORDER BY releaseDate DESC")
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryFavoriteMovies(): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM filmEntity where favorite = 1 and isTvShow = 0 ORDER BY releaseDate DESC")
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryFavoriteTvShows(): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM filmEntity where Favorite = 1 and isTvShow = 1 ORDER BY releaseDate DESC")
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}