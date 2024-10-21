package com.aquarius.indra_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquarius.indra_challenge.data.MovieEntity
import com.aquarius.indra_challenge.repository.MovieRepository
import com.aquarius.indra_challenge.util.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val movies: LiveData<List<MovieEntity>> = repository.movies

    init {
        fetchMovies(1, Constants.API_KEY)
    }

    fun fetchMovies(page: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                repository.fetchMovies(page, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}