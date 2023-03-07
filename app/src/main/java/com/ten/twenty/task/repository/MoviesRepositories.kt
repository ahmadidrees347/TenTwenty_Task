package com.ten.twenty.task.repository

import androidx.paging.PagingData
import com.ten.twenty.task.model.MovieDetailModel
import com.ten.twenty.task.model.MovieResults
import com.ten.twenty.task.model.MovieTrailerModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepositories {

    fun getAllMovies(): Flow<PagingData<MovieResults>>

    fun searchMoviesByQuery(query: String): Flow<PagingData<MovieResults>>

    suspend fun getMovieDetailById(movieId: Int): Flow<MovieDetailModel?>

    suspend fun getMovieTrailer(movieId: Int): Flow<MovieTrailerModel?>
}