package com.example.madecapstone.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var releaseDate: String,
    var overview: String,
    var popularity: Double,
    var voteAverage: Double,
    var voteCount: Int,
    var posterPath: String,
    var favorite: Boolean = false,
    var isTvShows: Boolean = false
) : Parcelable