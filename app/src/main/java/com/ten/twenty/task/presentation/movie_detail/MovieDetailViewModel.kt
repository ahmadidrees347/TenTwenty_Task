package com.ten.twenty.task.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ten.twenty.task.domain.usecase.MovieDetailUseCase
import com.ten.twenty.task.data.network.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieDetailUseCase: MovieDetailUseCase
) : ViewModel() {
    private val defaultError = "An Unexpected Error"

    private val _moviesDetailData = MutableStateFlow(MovieDetailState())
    var moviesDetailData: StateFlow<MovieDetailState> = _moviesDetailData

    fun getMovieDetail(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieDetailUseCase(id = movieId).collect {
            when (it) {
                is ResponseState.Loading -> {
                    _moviesDetailData.value = MovieDetailState(isLoading = true)
                }
                is ResponseState.Success -> {
                    _moviesDetailData.value =
                        MovieDetailState(isLoading = false, movieModel = it.data)
                }
                is ResponseState.Error -> {
                    _moviesDetailData.value =
                        MovieDetailState(isLoading = false, error = it.message ?: defaultError)
                }
            }
        }
    }
}