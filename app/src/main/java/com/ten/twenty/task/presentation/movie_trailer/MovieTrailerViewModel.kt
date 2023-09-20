package com.ten.twenty.task.presentation.movie_trailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ten.twenty.task.domain.usecase.MovieTrailerUseCase
import com.ten.twenty.task.data.network.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieTrailerViewModel(
    private val movieDetailUseCase: MovieTrailerUseCase
) : ViewModel() {
    private val defaultError = "An Unexpected Error"

    private val _moviesTrailerData = MutableStateFlow(MovieTrailerState())
    var moviesTrailerData: StateFlow<MovieTrailerState> = _moviesTrailerData

    fun getMovieTrailerById(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieDetailUseCase(id = movieId).collect {
            when (it) {
                is ResponseState.Loading -> {
                    _moviesTrailerData.value = MovieTrailerState(isLoading = true)
                }
                is ResponseState.Success -> {
                    _moviesTrailerData.value =
                        MovieTrailerState(isLoading = false, trailerModel = it.data)
                }
                is ResponseState.Error -> {
                    _moviesTrailerData.value =
                        MovieTrailerState(isLoading = false, error = it.message ?: defaultError)
                }
            }
        }
    }
}