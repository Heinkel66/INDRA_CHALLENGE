package com.aquarius.indra_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquarius.indra_challenge.data.HeaderEntity
import com.aquarius.indra_challenge.data.MovieEntity
import com.aquarius.indra_challenge.repository.HeaderRepository
import com.aquarius.indra_challenge.repository.MovieRepository
import com.aquarius.indra_challenge.util.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieViewModel(
    private val repository: MovieRepository,
    headerRepository: HeaderRepository
) : ViewModel() {

    private var currentPage = 1
    val movies: LiveData<List<MovieEntity>> = repository.movies
    val header: LiveData<HeaderEntity> = headerRepository.header

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isNextPageEnabled = MutableLiveData(true)
    val isNextPageEnabled: LiveData<Boolean> get() = _isNextPageEnabled

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            // Iniciar el estado de carga
            _isLoading.postValue(true)
            try {
                repository.fetchMovies(currentPage, Constants.API_KEY)
                checkNextPageAvailability()
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun nextPage() {
        val totalPages = header.value?.total_pages

        if (totalPages != null && currentPage < totalPages) {
            currentPage++
            fetchMovies()
        } else {
            println("Se alcanzó el límite de páginas")
            _isNextPageEnabled.postValue(false)
            _error.postValue("No puedes avanzar a la siguiente página. Límite alcanzado.")
        }
    }

    fun previousPage() {
        if (currentPage > 1) {
            currentPage--
            fetchMovies()
            _isNextPageEnabled.postValue(true)
        } else {
            println("Ya estás en la primera página")
            fetchMovies()
        }
    }

    private fun checkNextPageAvailability() {
        val totalPages = header.value?.total_pages
        _isNextPageEnabled.postValue(totalPages == null || currentPage < totalPages)
    }

    fun clearError() {
        _error.value = null
    }
}