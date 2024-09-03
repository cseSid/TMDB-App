package org.sid.tmdbapp.data.remote.api

import org.sid.tmdbapp.data.model.TrendingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TmdbApiService {
    @GET("trending/movie/day")
    suspend fun getPopularMovies(
        @Query("language") language: String
    ): TrendingMovieResponse
}