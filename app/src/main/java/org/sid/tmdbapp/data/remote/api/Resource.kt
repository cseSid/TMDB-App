package org.sid.tmdbapp.data.remote.api

import org.sid.tmdbapp.data.model.ErrorResponse
import org.sid.tmdbapp.data.model.TrendingMovieResponse

sealed class Resource{
    object Loading: Resource()
    data class Success(val trendingMovieResponse: TrendingMovieResponse) : Resource()
    data class Error(val errorResponse: ErrorResponse) : Resource()
}
