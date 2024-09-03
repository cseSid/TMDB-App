package org.sid.tmdbapp.domain

import org.json.JSONException
import org.json.JSONObject
import org.sid.tmdbapp.data.model.ErrorResponse
import org.sid.tmdbapp.data.model.TrendingMovieResponse
import org.sid.tmdbapp.data.repository.MovieRepository
import retrofit2.HttpException
import javax.inject.Inject


class GetTrendingMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): TrendingMovieResponse {
        try {
            return movieRepository.getPopularMovies()
        } catch (e: HttpException) {
            val errorResponse = e.response()?.errorBody()?.string()
            val parsedErrorResponse = parseErrorResponse(errorResponse)
            throw ApiException(parsedErrorResponse)
        }
    }

    private fun parseErrorResponse(errorResponse: String?): ErrorResponse {
        // Assuming the error response is in JSON format
        return try {
            val jsonObject = JSONObject(errorResponse ?: "{}")
            ErrorResponse(
                success = jsonObject.optBoolean("success"),
                statusCode = jsonObject.optInt("status_code"),
                statusMessage = jsonObject.optString("status_message")
            )
        } catch (e: JSONException) {
            ErrorResponse(
                success = false,
                statusCode = null,
                statusMessage = "Parsing error response failed"
            )
        }
    }
}

// domain/ApiException.kt
data class ApiException(val errorResponse: ErrorResponse) : Exception(errorResponse.statusMessage)
