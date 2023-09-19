package com.ten.twenty.task.di

import com.ten.twenty.task.data.data_source.MoviesAPI
import com.ten.twenty.task.data.repository.MoviesRepoImpl
import com.ten.twenty.task.domain.repository.MoviesRepositories
import com.ten.twenty.task.domain.use_case.MovieDetailUseCase
import com.ten.twenty.task.domain.use_case.MovieTrailerUseCase
import com.ten.twenty.task.presentation.movie_detail.MovieDetailViewModel
import com.ten.twenty.task.presentation.movie_trailer.MovieTrailerViewModel
import com.ten.twenty.task.presentation.viewmodel.MoviesViewModel
import com.ten.twenty.task.presentation.viewmodel.MySharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val getModule = module {

        single { MoviesAPI() }
        single { MoviesRepoImpl(get()) }

        single<MoviesRepositories> { MoviesRepoImpl(get()) }

        single { MovieDetailUseCase(get()) }
        single { MovieTrailerUseCase(get()) }

        viewModel { MovieDetailViewModel(get()) }
        viewModel { MovieTrailerViewModel(get()) }

        viewModel { MoviesViewModel(get()) }
        viewModel { MySharedViewModel() }
    }
}



