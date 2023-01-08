package com.ten.twenty.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ten.twenty.task.model.MovieDetailState
import com.ten.twenty.task.model.MovieState
import com.ten.twenty.task.model.MovieTrailerState
import com.ten.twenty.task.repository.MoviesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MoviesViewModel(private val moviesRepo: MoviesRepo) : ViewModel() {

    private val _moviesData =
        MutableStateFlow<MovieState>(MovieState.Empty)
    val moviesData: StateFlow<MovieState> = _moviesData

    private val _moviesDetailData =
        MutableStateFlow<MovieDetailState>(MovieDetailState.Empty)
    val moviesDetailData: StateFlow<MovieDetailState> = _moviesDetailData

    private val _moviesTrailerData =
        MutableStateFlow<MovieTrailerState>(MovieTrailerState.Empty)
    val moviesTrailerData: StateFlow<MovieTrailerState> = _moviesTrailerData


    fun getAllMovies() =
        viewModelScope.launch {
            moviesRepo.getAllMovies()
                .onStart {
                    _moviesData.value = MovieState.Loading
                }
                .catch { e ->
                    _moviesData.value = MovieState.Failure(e.toString())
                }
                .collect { pagingData ->
                    _moviesData.value = MovieState.Success(pagingData)
                }
        }

    fun searchMoviesByQuery(query: String) =
        viewModelScope.launch {
            moviesRepo.searchMoviesByQuery(query)
                .onStart {
                    _moviesData.value = MovieState.Loading
                }
                .catch { e ->
                    _moviesData.value = MovieState.Failure(e.toString())
                }
                .collect { pagingData ->
                    _moviesData.value = MovieState.Success(pagingData)
                }
        }

    fun getMovieDetailById(movieId: Int) =
        viewModelScope.launch {
            moviesRepo.getMovieDetailById(movieId)
                .onStart {
                    _moviesDetailData.value = MovieDetailState.Loading
                }
                .catch { e ->
                    _moviesDetailData.value = MovieDetailState.Failure(e.toString())
                }
                .collect {
                    it?.let {
                        _moviesDetailData.value = MovieDetailState.Success(it)
                    } ?: kotlin.run {
                        _moviesDetailData.value = MovieDetailState.Empty
                    }
                }
        }

    fun getMovieTrailerById(movieId: Int) =
        viewModelScope.launch {
            moviesRepo.getMovieTrailer(movieId)
                .onStart {
                    _moviesTrailerData.value = MovieTrailerState.Loading
                }
                .catch { e ->
                    _moviesTrailerData.value = MovieTrailerState.Failure(e.toString())
                }
                .collect {
                    it?.let {
                        _moviesTrailerData.value = MovieTrailerState.Success(it)
                    } ?: kotlin.run {
                        _moviesTrailerData.value = MovieTrailerState.Empty
                    }
                }
        }
}