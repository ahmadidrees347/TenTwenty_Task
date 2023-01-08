package com.ten.twenty.task.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ten.twenty.task.api.ApiResponseRequest
import com.ten.twenty.task.api.MoviesPagingDataSource
import com.ten.twenty.task.api.MoviesService
import com.ten.twenty.task.api.SearchMoviesPagingDataSource
import com.ten.twenty.task.model.MovieDetailModel
import com.ten.twenty.task.model.MovieResults
import com.ten.twenty.task.model.MovieTrailerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MoviesRepo(private val apiService: MoviesService) : ApiResponseRequest() {
    fun getAllMovies(): Flow<PagingData<MovieResults>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviesPagingDataSource(apiService) }
        ).flow
    }

    fun searchMoviesByQuery(query: String): Flow<PagingData<MovieResults>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchMoviesPagingDataSource(apiService, query) }
        ).flow
    }

    suspend fun getMovieDetailById(movieId: Int)
            : Flow<MovieDetailModel?> = flow {
        emit(apiRequest { apiService.getMovieDetailById(movieId) })
    }.flowOn(Dispatchers.Default)

    suspend fun getMovieTrailer(movieId: Int)
            : Flow<MovieTrailerModel?> = flow {
        emit(apiRequest { apiService.getMovieTrailer(movieId) })
    }.flowOn(Dispatchers.Default)

}