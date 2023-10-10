package com.ten.twenty.task.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ten.twenty.task.data.source.paging.MoviesPaging
import com.ten.twenty.task.data.source.paging.SearchMoviesPaging
import com.ten.twenty.task.data.source.MoviesAPI
import com.ten.twenty.task.data.source.dto.MovieDetailModel
import com.ten.twenty.task.data.source.dto.MovieResults
import com.ten.twenty.task.data.source.dto.MovieTrailerModel
import com.ten.twenty.task.domain.repository.MoviesRepositories
import com.ten.twenty.task.data.network.ApiResponseRequest
import kotlinx.coroutines.flow.Flow

class MoviesRepoImpl(private val apiService: MoviesAPI) : ApiResponseRequest(), MoviesRepositories {
    override fun getAllMovies(): Flow<PagingData<MovieResults>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviesPaging(apiService) }).flow
    }

    override fun searchMoviesByQuery(query: String): Flow<PagingData<MovieResults>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchMoviesPaging(apiService, query) }).flow
    }

    override suspend fun getMovieDetailById(movieId: Int): MovieDetailModel =
        apiRequest { apiService.getMovieDetailById(movieId) } ?: MovieDetailModel(0, "", "")

    override suspend fun getMovieTrailer(movieId: Int): MovieTrailerModel =
        apiRequest { apiService.getMovieTrailer(movieId) } ?: MovieTrailerModel(0, arrayListOf())

}