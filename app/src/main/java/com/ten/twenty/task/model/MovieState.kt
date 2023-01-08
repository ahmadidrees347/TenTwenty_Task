package com.ten.twenty.task.model

import androidx.paging.PagingData


sealed class MovieState {
    object Empty : MovieState()
    object Loading : MovieState()
    class Failure(val error: String) : MovieState()
    data class Success(val moviesList: PagingData<MovieResults>) : MovieState()
}