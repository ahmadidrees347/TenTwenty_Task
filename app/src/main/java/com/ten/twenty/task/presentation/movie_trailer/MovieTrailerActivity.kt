package com.ten.twenty.task.presentation.movie_trailer

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.ten.twenty.task.databinding.ActivityMovieTrailerBinding
import com.ten.twenty.task.presentation.BaseActivity
import com.ten.twenty.task.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MovieTrailerActivity : BaseActivity<ActivityMovieTrailerBinding>() {
    private val tag = "MovieTrailer*"
    private val movieTrailerViewModel by viewModel<MovieTrailerViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityMovieTrailerBinding
        get() = { ActivityMovieTrailerBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMovieTrailerDetail()
    }

    private fun getMovieTrailerDetail() {
        val movieId = intent?.getIntExtra(Constants.MOVIE_ID, -1) ?: -1
        if (movieId != -1) {
            movieTrailerViewModel.getMovieTrailerById(movieId)
        }
        lifecycleScope.launch {
            movieTrailerViewModel.moviesTrailerData.collectLatest { movieTrailer ->
                if (!movieTrailer.isLoading) {
                    if (movieTrailer.error.isNotEmpty()) {
                        Timber.tag(tag).e("*Response: %s", movieTrailer.error)
                    } else {
                        val results = movieTrailer.trailerModel?.results ?: arrayListOf()
                        if (results.isNotEmpty()) {
                            initializePlayer(results[0].key)
                        } else {
                            Timber.tag(tag).e("*Response: List is Empty")
                        }
                    }
                } else {
                    Timber.tag(tag).e("*Response: Loading")
                }
            }
        }
    }

    private fun initializePlayer(videoId: String) {
        with(binding) {
            lifecycle.addObserver(youtubePlayerView)
            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState
                ) {
                    if (state == PlayerConstants.PlayerState.ENDED) onBackPress()
                }
            })
        }
    }
}