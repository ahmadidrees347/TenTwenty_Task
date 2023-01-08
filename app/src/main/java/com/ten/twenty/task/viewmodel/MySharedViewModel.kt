package com.ten.twenty.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MySharedViewModel : ViewModel() {

    val searchedMovie = MutableLiveData<String>()

    fun searchMovies(query: String) =
        viewModelScope.launch {
            searchedMovie.value = query
        }
}