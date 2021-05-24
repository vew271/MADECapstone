package com.example.madecapstone.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.madecapstone.core.data.source.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDAO {
    @RawQuery(observedEntities = [FilmEntity::class])
    fun getMovies(query: SupportSQLiteQuery): Flow<List<FilmEntity>>

    @RawQuery(observedEntities = [FilmEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): Flow<List<FilmEntity>>

    @Query("SELECT * FROM filmEntity WHERE isTvShow = 0 AND title LIKE '%' || :search || '%'")
    fun getSearchMovies(search: String): Flow<List<FilmEntity>>

    @Query("SELECT * FROM filmEntity WHERE isTvShow = 1 AND title LIKE '%' || :search || '%'")
    fun getSearchTvShows(search: String): Flow<List<FilmEntity>>

    @RawQuery(observedEntities = [FilmEntity::class])
    fun getFavoriteMovies(query: SupportSQLiteQuery): Flow<List<FilmEntity>>

    @RawQuery(observedEntities = [FilmEntity::class])
    fun getFavoriteTvShows(query: SupportSQLiteQuery): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<FilmEntity>)

    @Update
    fun updateFavoriteMovie(movie: FilmEntity)
}