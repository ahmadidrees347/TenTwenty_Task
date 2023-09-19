package com.ten.twenty.task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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