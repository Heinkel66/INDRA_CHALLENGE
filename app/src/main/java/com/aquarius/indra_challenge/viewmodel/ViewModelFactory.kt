package com.aquarius.indra_challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aquarius.indra_challenge.repository.HeaderRepository
import com.aquarius.indra_challenge.repository.MovieRepository
import com.aquarius.indra_challenge.repository.UserRepository

class ViewModelFactory(
    private val userRepository: UserRepository?,
    private val movieRepository: MovieRepository?,
    private val headerRepository: HeaderRepository?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) && userRepository != null -> {
                LoginViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(MovieViewModel::class.java) && movieRepository != null && headerRepository != null -> {
                MovieViewModel(movieRepository, headerRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}