package com.ten.twenty.task.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ten.twenty.task.viewmodel.MoviesViewModel
import com.ten.twenty.task.viewmodel.MySharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment : Fragment() {
    protected val moviesViewModel by viewModel<MoviesViewModel>()
    protected val mySharedViewModel by activityViewModels<MySharedViewModel>()
}