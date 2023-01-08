package com.ten.twenty.task.model

import com.google.gson.annotations.SerializedName
import com.ten.twenty.task.extension.Constants

data class MovieDetailModel(
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?
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