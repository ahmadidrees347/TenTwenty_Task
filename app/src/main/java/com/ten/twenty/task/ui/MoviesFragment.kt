package com.ten.twenty.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ten.twenty.task.adapter.MovieAdapter
import com.ten.twenty.task.databinding.FragmentMoviesBinding
import com.ten.twenty.task.extension.Constants
import com.ten.twenty.task.extension.openActivity
import com.ten.twenty.task.model.MovieState
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class MoviesFragment : BaseFragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val movieAdapter by lazy { MovieAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getAllMovies()
    }

    private fun initViews() {
        with(binding) {
            movieAdapter.isSearchItems = false
            movieAdapter.onMovieClick = { movieModel ->
                activity?.openActivity<MovieDetailActivity> {
                    putExtra(Constants.MOVIE_ID, movieModel.id)
                }
            }
            recyclerMovies.layoutManager = LinearLayoutManager(context)
            recyclerMovies.adapter = movieAdapter
        }
    }

    private fun getAllMovies() {
        moviesViewModel.getAllMovies()
        lifecycleScope.launchWhenResumed {
            moviesViewModel.moviesData.collectLatest {
                when (it) {
                    is MovieState.Loading -> {
                        Timber.tag("movieData*").e("*Response: Loading")
                    }
                    is MovieState.Success -> {
                        Timber.tag("movieData*").e("*Response: Success")
                        movieAdapter.submitData(it.moviesList)
                    }
                    is MovieState.Failure -> {
                        Timber.tag("movieData*").e("*Response: %s", it.error)
                    }
                    is MovieState.Empty -> {
                        Timber.tag("movieData*").e("*Response: Empty")
                    }
                }
            }
        }
    }
}