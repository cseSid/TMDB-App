package org.sid.tmdbapp.data.repository

import org.sid.tmdbapp.data.model.TrendingMovieResponse
import org.sid.tmdbapp.data.remote.MovieRemoteDataSource
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(private val remoteDataSource: MovieRemoteDataSource) : MovieRepository {
    override suspend fun getPopularMovies(): TrendingMovieResponse {
        return remoteDataSource.getTrendingMovies()
    }


}