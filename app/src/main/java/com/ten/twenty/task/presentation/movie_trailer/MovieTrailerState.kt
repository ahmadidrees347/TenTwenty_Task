package com.ten.twenty.task.presentation.movie_trailer

import com.ten.twenty.task.data.source.dto.MovieTrailerModel

data class MovieTrailerState(
    var isLoading: Boolean = false,
    var trailerModel: MovieTrailerModel? = null,
    var error: String = ""
)