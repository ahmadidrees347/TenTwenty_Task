package com.ten.twenty.task.presentation.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import coil.load
import com.ten.twenty.task.R
import com.ten.twenty.task.data.source.dto.MovieDetailModel
import com.ten.twenty.task.databinding.ActivityMovieDetailBinding
import com.ten.twenty.task.presentation.BaseActivity
import com.ten.twenty.task.presentation.movie_trailer.MovieTrailerActivity
import com.ten.twenty.task.utils.Constants
import com.ten.twenty.task.utils.openActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MovieDetailActivity : BaseActivity<ActivityMovieDetailBinding>() {
    private val tag = "movieDetail*"
    private val movieDetailViewModel by viewModel<MovieDetailViewModel>()

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
            movieDetailViewModel.getMovieDetail(movieId)
        }
        lifecycleScope.launch {
            movieDetailViewModel.moviesDetailData.collectLatest { movieDetail ->
                if (!movieDetail.isLoading) {
                    if (movieDetail.error.isNotEmpty()) {
                        Timber.tag(tag).e("*Response: %s", movieDetail.error)
                    } else {
                        movieDetail.movieModel?.let { setMovieData(it) }
                            ?: kotlin.run {
                                Timber.tag(tag).e("*Response: Model is Null")
                            }
                    }
                } else {
                    Timber.tag(tag).e("*Response: Loading")
                }
            }
        }
    }

    private fun setMovieData(movieModel: MovieDetailModel) {
        with(binding) {
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