package com.ten.twenty.task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ten.twenty.task.data.repository.MoviesRepoImpl
import com.ten.twenty.task.presentation.all_movies.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MoviesViewModel(private val moviesRepoImpl: MoviesRepoImpl) : ViewModel() {

    private val _moviesData =
        MutableStateFlow<MovieState>(MovieState.Empty)
    val moviesData: StateFlow<MovieState> = _moviesData

    fun getAllMovies() =
        viewModelScope.launch {
            moviesRepoImpl.getAllMovies().cachedIn(this)
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
            moviesRepoImpl.searchMoviesByQuery(query).cachedIn(this)
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
}