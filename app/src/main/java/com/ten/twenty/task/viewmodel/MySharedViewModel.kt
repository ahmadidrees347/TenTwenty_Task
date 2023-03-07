package com.ten.twenty.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ten.twenty.task.model.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MySharedViewModel : ViewModel() {


    private val _searchedMovie =
        MutableStateFlow("")
    val searchedMovie: StateFlow<String> = _searchedMovie

//    val searchedMovie = MutableLiveData<String>()

    fun searchMovies(query: String) =
        viewModelScope.launch {
            _searchedMovie.value = query
        }
}