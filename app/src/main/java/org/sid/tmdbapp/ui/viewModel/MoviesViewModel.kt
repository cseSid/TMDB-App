package org.sid.tmdbapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sid.tmdbapp.data.model.ErrorResponse
import org.sid.tmdbapp.data.model.Results
import org.sid.tmdbapp.data.remote.api.Resource
import org.sid.tmdbapp.domain.ApiException
import org.sid.tmdbapp.domain.GetTrendingMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase) : ViewModel(){

    private val _getTrendingMovies = MutableLiveData<Resource>(Resource.Loading)
    val getTrendingMovie: LiveData<Resource> = _getTrendingMovies


    private val _selectedMovie = MutableLiveData<Results>()
    val selectedMovie: LiveData<Results> get() = _selectedMovie

    fun selectMovie(movie: Results) {
        _selectedMovie.value = movie
    }

   init {
       viewModelScope.launch {
           try {
               val response = getTrendingMoviesUseCase()
               _getTrendingMovies.postValue(Resource.Success(response))
           } catch (e: ApiException) {
               val errorResponse = e.errorResponse
               _getTrendingMovies.postValue(Resource.Error(errorResponse))
           } catch (e: Exception) {
               val errorResponse = ErrorResponse(
                   success = false,
                   statusCode = null,
                   statusMessage = "Unexpected error: ${e.message}"
               )
               _getTrendingMovies.postValue(Resource.Error(errorResponse))
           }
       }
   }




}