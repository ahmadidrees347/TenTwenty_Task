package com.ten.twenty.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.ten.twenty.task.R
import com.ten.twenty.task.databinding.ActivityMovieDetailBinding
import com.ten.twenty.task.extension.Constants
import com.ten.twenty.task.extension.openActivity
import com.ten.twenty.task.model.MovieDetailModel
import com.ten.twenty.task.model.MovieDetailState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailActivity : BaseActivity<ActivityMovieDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMovieDetailBinding
        get() = {
            ActivityMovieDetailBinding.inflate(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMovieDetail()
    }

    private fun getMovieDetail() {
        val movieId = intent?.getIntExtra(Constants.MOVIE_ID, -1) ?: -1
        if (movieId != -1) {
            moviesViewModel.getMovieDetailById(movieId)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.moviesDetailData.collectLatest {
                    when (it) {
                        is MovieDetailState.Loading -> {
                            Timber.tag("movieDetail*").e("*Response: Loading")
                        }
                        is MovieDetailState.Success -> {
                            Timber.tag("movieDetail*").e("*Response: Success")
                            setMovieData(it.movieModel)
                        }
                        is MovieDetailState.Failure -> {
                            Timber.tag("movieDetail*").e("*Response: %s", it.error)
                        }
                        is MovieDetailState.Empty -> {
                            Timber.tag("movieDetail*").e("*Response: Empty")
                        }
                    }
                }
            }
        }
    }

    private fun setMovieData(movieModel: MovieDetailModel) {
        with(binding) {
//            Glide.with(this@MovieDetailActivity)
//                .load(movieModel.getUrlImage())
//                .into(imgMoviePoster)
            imgMoviePoster.load(movieModel.getUrlImage()) {
                crossfade(true)
                placeholder(R.drawable.ic_image_load)
                error(R.drawable.ic_image_load)
//                    transformations(CircleCropTransformation())
            }
            txtMovieTitle.text = movieModel.title
            txtOverViewContent.text = movieModel.overview
            val strDate = "In theaters " + movieModel.releaseDate
            txtMovieReleaseDate.text = strDate
            btnWatchMovie.setOnClickListener {
                openActivity<MovieTrailerActivity> {
                    putExtra(Constants.MOVIE_ID, movieModel.id)
                }
            }
        }
    }
}