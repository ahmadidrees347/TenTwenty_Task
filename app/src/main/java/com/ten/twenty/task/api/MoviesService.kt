package com.ten.twenty.task.api

import com.ten.twenty.task.model.MovieDetailModel
import com.ten.twenty.task.model.MovieModel
import com.ten.twenty.task.model.MovieTrailerModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesService {

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
        operator fun invoke(): MoviesService {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpProvider.instance)
                .build()
                .create(MoviesService::class.java)
        }
    }
}

