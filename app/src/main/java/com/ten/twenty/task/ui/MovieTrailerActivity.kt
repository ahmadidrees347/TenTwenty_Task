package com.ten.twenty.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.ten.twenty.task.databinding.ActivityMovieTrailerBinding
import com.ten.twenty.task.extension.Constants
import com.ten.twenty.task.model.MovieTrailerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class MovieTrailerActivity : BaseActivity<ActivityMovieTrailerBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMovieTrailerBinding
        get() = { ActivityMovieTrailerBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMovieTrailerDetail()
    }

    private fun getMovieTrailerDetail() {
        val movieId = intent?.getIntExtra(Constants.MOVIE_ID, -1) ?: -1
        if (movieId != -1) {
            moviesViewModel.getMovieTrailerById(movieId)
        }
        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
            moviesViewModel.moviesTrailerData.collectLatest {
                when (it) {
                    is MovieTrailerState.Loading -> {
                        Timber.tag("movieTrailer*").e("*Response: Loading")
                    }
                    is MovieTrailerState.Success -> {
                        Timber.tag("movieTrailer*")
                            .e("*Response: Success : %s", it.trailerModel.id)
                        val results = it.trailerModel.results
                        if (results.isNotEmpty()) {
                            initializePlayer(results[0].key)
                        }
                    }
                    is MovieTrailerState.Failure -> {
                        Timber.tag("movieTrailer*").e("*Response: %s", it.error)
                    }
                    is MovieTrailerState.Empty -> {
                        Timber.tag("movieTrailer*").e("*Response: Empty")
                    }
                }
            }
//            }
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