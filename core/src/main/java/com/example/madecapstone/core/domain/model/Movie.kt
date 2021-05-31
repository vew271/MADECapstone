package com.example.madecapstone.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var releaseDate: String,
    var overview: String,
    var posterPath: String,
    var backdropPath: String,
    var favorite: Boolean = false,
    var isTvShows: Boolean = false
) : Parcelable