package com.ten.twenty.task.data.data_source.dto

import com.google.gson.annotations.SerializedName
import com.ten.twenty.task.utils.Constants

data class MovieDetailModel(
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null
) {
    fun getUrlImage(): String {
        return if (posterPath.isNullOrEmpty())
            String.format(
                Constants.BASE_IMG_342_PATH,
                posterPath
            )
        else
            String.format(
                Constants.BASE_IMG_780_PATH,
                backdropPath
            )
    }
}