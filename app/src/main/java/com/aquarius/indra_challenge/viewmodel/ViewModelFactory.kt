package com.aquarius.indra_challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aquarius.indra_challenge.repository.MovieRepository
import com.aquarius.indra_challenge.repository.UserRepository

class ViewModelFactory(
    private val userRepository: UserRepository?,
    private val movieRepository: MovieRepository?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) && userRepository != null -> {
                LoginViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(MovieViewModel::class.java) && movieRepository != null -> {
                MovieViewModel(movieRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}