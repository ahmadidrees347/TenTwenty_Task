package com.ten.twenty.task.model


sealed class MovieTrailerState {
    object Empty : MovieTrailerState()
    object Loading : MovieTrailerState()
    class Failure(val error: String) : MovieTrailerState()
    data class Success(val trailerModel: MovieTrailerModel) : MovieTrailerState()
}