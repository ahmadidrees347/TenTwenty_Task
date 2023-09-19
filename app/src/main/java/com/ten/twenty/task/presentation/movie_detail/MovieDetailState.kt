package com.ten.twenty.task.presentation.movie_detail

import com.ten.twenty.task.data.data_source.dto.MovieDetailModel

data class MovieDetailState(
    var isLoading: Boolean = false,
    var movieModel: MovieDetailModel? = null,
    var error: String = ""
)