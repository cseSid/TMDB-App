package org.sid.tmdbapp.data.repository

import org.sid.tmdbapp.data.model.TrendingMovieResponse

interface MovieRepository {
    suspend fun getPopularMovies(): TrendingMovieResponse

}