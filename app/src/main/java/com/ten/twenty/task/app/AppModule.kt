package com.ten.twenty.task.app

import com.ten.twenty.task.api.MoviesService
import com.ten.twenty.task.repository.MoviesRepo
import com.ten.twenty.task.viewmodel.MoviesViewModel
import com.ten.twenty.task.viewmodel.MySharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val getModule = module {

        single { MoviesService() }
        single { MoviesRepo(get()) }

        viewModel { MoviesViewModel(get()) }
        viewModel { MySharedViewModel() }
    }
}