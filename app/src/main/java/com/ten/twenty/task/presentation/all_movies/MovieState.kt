package com.ten.twenty.task.presentation.all_movies

import androidx.paging.PagingData
import com.ten.twenty.task.data.data_source.dto.MovieResults


sealed class MovieState {
    object Empty : MovieState()
    object Loading : MovieState()
    class Failure(val error: String) : MovieState()
    data class Success(val moviesList: PagingData<MovieResults>) : MovieState()
}