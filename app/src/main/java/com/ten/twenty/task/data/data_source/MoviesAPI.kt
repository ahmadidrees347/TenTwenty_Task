package com.ten.twenty.task.data.data_source

import com.ten.twenty.task.api.OkHttpProvider
import com.ten.twenty.task.data.data_source.dto.MovieDetailModel
import com.ten.twenty.task.data.data_source.dto.MovieModel
import com.ten.twenty.task.data.data_source.dto.MovieTrailerModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesAPI {

    @GET("3/movie/upcoming")
    suspend fun getAllUpcomingMovies(@Query("page") page: Int = 1): Response<MovieModel>

    @GET("3/movie/{movieId}")
    suspend fun getMovieDetailById(@Path("movieId") movieId: Int): Response<MovieDetailModel>

    @GET("3/search/movie")
    suspend fun searchMoviesByQuery(
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ): Response<MovieModel>

    @GET("3/movie/{movieId}/videos")
    suspend fun getMovieTrailer(@Path("movieId") movieId: Int): Response<MovieTrailerModel>

    companion object {
        operator fun invoke(): MoviesAPI {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpProvider.instance)
                .build()
                .create(MoviesAPI::class.java)
        }
    }
}

