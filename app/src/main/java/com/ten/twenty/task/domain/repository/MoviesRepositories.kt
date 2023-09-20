package com.ten.twenty.task.domain.repository

import androidx.paging.PagingData
import com.ten.twenty.task.data.source.dto.MovieDetailModel
import com.ten.twenty.task.data.source.dto.MovieResults
import com.ten.twenty.task.data.source.dto.MovieTrailerModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepositories {

    fun getAllMovies(): Flow<PagingData<MovieResults>>

    fun searchMoviesByQuery(query: String): Flow<PagingData<MovieResults>>

    suspend fun getMovieDetailById(movieId: Int): MovieDetailModel

    suspend fun getMovieTrailer(movieId: Int): MovieTrailerModel
}