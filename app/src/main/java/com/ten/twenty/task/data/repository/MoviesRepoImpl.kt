package com.ten.twenty.task.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ten.twenty.task.api.MoviesPagingDataSource
import com.ten.twenty.task.api.SearchMoviesPagingDataSource
import com.ten.twenty.task.data.data_source.MoviesAPI
import com.ten.twenty.task.data.data_source.dto.MovieDetailModel
import com.ten.twenty.task.data.data_source.dto.MovieResults
import com.ten.twenty.task.data.data_source.dto.MovieTrailerModel
import com.ten.twenty.task.domain.repository.MoviesRepositories
import com.ten.twenty.task.utils.ApiResponseRequest
import kotlinx.coroutines.flow.Flow

class MoviesRepoImpl(private val apiService: MoviesAPI) : ApiResponseRequest(), MoviesRepositories {
    override fun getAllMovies(): Flow<PagingData<MovieResults>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviesPagingDataSource(apiService) }).flow
    }

    override fun searchMoviesByQuery(query: String): Flow<PagingData<MovieResults>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchMoviesPagingDataSource(apiService, query) }).flow
    }

    override suspend fun getMovieDetailById(movieId: Int): MovieDetailModel =
        apiRequest { apiService.getMovieDetailById(movieId) } ?: MovieDetailModel(0, "", "")

    override suspend fun getMovieTrailer(movieId: Int): MovieTrailerModel =
        apiRequest { apiService.getMovieTrailer(movieId) } ?: MovieTrailerModel(0, arrayListOf())

}