package com.aquarius.indra_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquarius.indra_challenge.data.MovieEntity
import com.aquarius.indra_challenge.repository.MovieRepository
import com.aquarius.indra_challenge.util.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private var currentPage = 1
    val movies: LiveData<List<MovieEntity>> = repository.movies
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            // Iniciar el estado de carga
            _isLoading.postValue(true)
            try {
                repository.fetchMovies(currentPage, Constants.API_KEY)
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun nextPage() {
        currentPage++
        fetchMovies()
    }

    fun previousPage() {
        if (currentPage > 1) {
            currentPage--
            fetchMovies()
        } else {
            println("Ya estás en la primera página")
            fetchMovies()
        }
    }

    fun clearError() {
        _error.value = null
    }
}