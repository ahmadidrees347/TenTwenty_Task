package com.ten.twenty.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ten.twenty.task.adapter.MovieAdapter
import com.ten.twenty.task.databinding.FragmentSearchBinding
import com.ten.twenty.task.extension.Constants
import com.ten.twenty.task.extension.openActivity
import com.ten.twenty.task.model.MovieState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val movieAdapter by lazy { MovieAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setListeners()
    }

    private fun initAdapter() {
        with(binding) {
            movieAdapter.isSearchItems = true
            movieAdapter.onMovieClick = { movieModel ->
                activity?.openActivity<MovieDetailActivity> {
                    putExtra(Constants.MOVIE_ID, movieModel.id)
                }
            }
            recyclerSearchedMovies.layoutManager = LinearLayoutManager(context)
            recyclerSearchedMovies.adapter = movieAdapter
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                /* mySharedViewModel.searchedMovie.observe(viewLifecycleOwner) { query ->
                     Timber.tag("movieData*").e("Search $query")
                     if (query.isNotEmpty())
                         moviesViewModel.searchMoviesByQuery(query)
                 }*/
                launch {
                    mySharedViewModel.searchedMovie.collect { query ->
                        Timber.tag("movieData*").e("Search $query")
                        if (query.isNotEmpty())
                            moviesViewModel.searchMoviesByQuery(query)
                    }
                }
                launch {
                    moviesViewModel.moviesData.collectLatest {
                        when (it) {
                            is MovieState.Loading -> {
                                Timber.tag("search*").e("*Response: Loading")
                            }
                            is MovieState.Success -> {
                                movieAdapter.submitData(it.moviesList)
                                Timber.tag("search*").e("*Response: Success")
                            }
                            is MovieState.Failure -> {
                                Timber.tag("search*").e("*Response: %s", it.error)
                            }
                            is MovieState.Empty -> {
                                Timber.tag("search*").e("*Response: Empty")
                            }
                        }
                    }
                }
            }

        }

    }
}