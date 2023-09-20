package com.ten.twenty.task.presentation.all_movies

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ten.twenty.task.R
import com.ten.twenty.task.data.source.dto.MovieResults
import com.ten.twenty.task.databinding.ItemMovieBinding
import com.ten.twenty.task.databinding.ItemSearchMovieBinding

class MovieAdapter(
    private val context: Context
) : PagingDataAdapter<MovieResults, RecyclerView.ViewHolder>(ItemDiffCallback()) {
    var onMovieClick: ((model: MovieResults) -> Unit)? = null
    var isSearchItems = false
//    private val requestOptions =
//        RequestOptions().placeholder(R.drawable.ic_image_load)
//            .error(R.drawable.ic_image_load)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (isSearchItems) {
            val binding = ItemSearchMovieBinding.inflate(layoutInflater, parent, false)
            ItemSearchMovieViewHolder(binding)
        } else {
            val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            ItemMoviesResViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            if (holder is ItemSearchMovieViewHolder)
                holder.bind(it)
            if (holder is ItemMoviesResViewHolder)
                holder.bind(it)
        }
    }

    inner class ItemMoviesResViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieResults) {
            with(binding) {
                txtMovieTitle.text = model.title
                imgMovie.setOnClickListener { onMovieClick?.invoke(model) }
//                Glide.with(context)
//                    .load(model.getUrlImage())
//                    .apply(requestOptions)
//                    .into(imgMovie)

                imgMovie.load(model.getUrlImage()) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_load)
                    error(R.drawable.ic_image_load)
//                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    inner class ItemSearchMovieViewHolder(
        private val binding: ItemSearchMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieResults) {
            with(binding) {
                txtMovieTitle.text = model.title
                imgMovie.setOnClickListener { onMovieClick?.invoke(model) }
//                Glide.with(context)
//                    .load(model.getUrlImage())
//                    .apply(requestOptions)
//                    .into(imgMovie)
                imgMovie.load(model.getUrlImage()) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_load)
                    error(R.drawable.ic_image_load)
//                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<MovieResults>() {
        override fun areItemsTheSame(
            oldItem: MovieResults,
            newItem: MovieResults
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MovieResults,
            newItem: MovieResults
        ): Boolean {
            return oldItem === newItem
        }
    }
}


