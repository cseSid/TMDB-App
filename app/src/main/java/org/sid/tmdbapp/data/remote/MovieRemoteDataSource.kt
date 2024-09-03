package org.sid.tmdbapp.data.remote

import org.sid.tmdbapp.data.model.TrendingMovieResponse
import org.sid.tmdbapp.data.remote.api.TmdbApiService
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val apiService: TmdbApiService) {

    suspend fun getTrendingMovies(): TrendingMovieResponse {
        return apiService.getPopularMovies("en-US")
    }

}