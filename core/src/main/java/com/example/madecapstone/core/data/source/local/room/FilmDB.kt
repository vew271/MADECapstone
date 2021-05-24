package com.example.madecapstone.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.madecapstone.core.data.source.local.entity.FilmEntity

@Database(entities = [FilmEntity::class], version = 1, exportSchema = false)
abstract class FilmDB : RoomDatabase() {
    abstract fun filmDao(): FilmDAO
}