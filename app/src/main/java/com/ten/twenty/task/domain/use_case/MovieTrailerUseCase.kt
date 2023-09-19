package com.ten.twenty.task.domain.use_case

import com.ten.twenty.task.data.data_source.dto.MovieTrailerModel
import com.ten.twenty.task.domain.repository.MoviesRepositories
import com.ten.twenty.task.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieTrailerUseCase(private val repository: MoviesRepositories) {

    operator fun invoke(id: Int): Flow<ResponseState<MovieTrailerModel>> = flow {
        try {
            emit(ResponseState.Loading())
            emit(ResponseState.Success(repository.getMovieTrailer(id)))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An Unexpected Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error("Internet Error"))
        }
    }
}