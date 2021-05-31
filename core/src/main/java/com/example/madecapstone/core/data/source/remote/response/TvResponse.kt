package com.example.madecapstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String
)

data class ListTv(
    @field:SerializedName("results")
    val results: List<TvResponse>
)
