package org.sid.tmdbapp.data.remote.api

import org.sid.tmdbapp.data.model.TrendingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): TrendingMovieResponse
}