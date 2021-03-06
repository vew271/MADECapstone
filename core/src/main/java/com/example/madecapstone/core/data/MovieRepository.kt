package com.example.madecapstone.core.data

import com.example.madecapstone.core.data.source.local.LocalDataSource
import com.example.madecapstone.core.data.source.remote.RemoteDataSource
import com.example.madecapstone.core.data.source.remote.network.ApiResponse
import com.example.madecapstone.core.data.source.remote.response.MovieResponse
import com.example.madecapstone.core.data.source.remote.response.TvResponse
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.domain.repository.CoreRepository
import com.example.madecapstone.core.utils.AppExecutor
import com.example.madecapstone.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutor: AppExecutor
) : CoreRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    Mapper.entityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = Mapper.movieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getSearchMovies(search: String): Flow<List<Movie>> {
        return localDataSource.getMovieSearch(search).map {
            Mapper.entityToDomain(it)
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteMovies().map {
            Mapper.entityToDomain(it)
        }
    }

    override fun getAllTvShows(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<TvResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllTvShows().map {
                    Mapper.entityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TvResponse>>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: List<TvResponse>) {
                val tvShowList = Mapper.tvResponsesToEntities(data)
                localDataSource.insertMovies(tvShowList)
            }
        }.asFlow()

    override fun getSearchTvShows(search: String): Flow<List<Movie>> {
        return localDataSource.getTvShowSearch(search).map {
            Mapper.entityToDomain(it)
        }
    }

    override fun getFavoriteTvShows(): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteTvShows().map {
            Mapper.entityToDomain(it)
        }
    }

    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        val movieEntity = Mapper.domainToEntity(movie)
        appExecutor.diskIO().execute { localDataSource.setMovieFavorite(movieEntity, state) }
    }
}