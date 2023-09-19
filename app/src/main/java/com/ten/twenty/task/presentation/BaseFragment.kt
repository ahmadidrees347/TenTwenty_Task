package com.ten.twenty.task.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ten.twenty.task.presentation.viewmodel.MoviesViewModel
import com.ten.twenty.task.presentation.viewmodel.MySharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment : Fragment() {
    protected val moviesViewModel by viewModel<MoviesViewModel>()
    protected val mySharedViewModel by activityViewModels<MySharedViewModel>()
}