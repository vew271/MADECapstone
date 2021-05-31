package com.example.madecapstone.core.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.madecapstone.core.domain.model.Movie

class DiffUtils(private val oldList: List<Movie>, private val newList: List<Movie>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_,
            title,
            releaseDate,
            overview,
            posterPath,
            backdropPath,
            favorite,
            isTvShows) = oldList[oldPosition]
        val (_,
            title1,
            releaseDate1,
            overview1,
            posterPath1,
            backdropPath1,
            favorite1,
            isTvShows1) = newList[newPosition]

        return overview == overview1
                && title == title1
                && releaseDate == releaseDate1
                && posterPath == posterPath1
                && backdropPath == backdropPath1
                && favorite == favorite1
                && isTvShows == isTvShows1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}