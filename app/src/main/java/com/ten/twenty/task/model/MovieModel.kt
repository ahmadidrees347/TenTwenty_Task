package com.ten.twenty.task.model

import com.google.gson.annotations.SerializedName
import com.ten.twenty.task.extension.Constants

data class MovieModel(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("results") val results: MutableList<MovieResults> = mutableListOf()
)

data class MovieResults(
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("vote_average") val voteAverage: Double
) {

    fun getUrlImage(): String {
        return if (!backdropPath.isNullOrEmpty())
            String.format(
                Constants.BASE_IMG_780_PATH,
                backdropPath
            )
        else
            String.format(
                Constants.BASE_IMG_780_PATH,
                posterPath
            )
    }
}