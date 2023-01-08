package com.ten.twenty.task.model


sealed class MovieDetailState {
    object Empty : MovieDetailState()
    object Loading : MovieDetailState()
    class Failure(val error: String) : MovieDetailState()
    data class Success(val movieModel: MovieDetailModel) : MovieDetailState()
}