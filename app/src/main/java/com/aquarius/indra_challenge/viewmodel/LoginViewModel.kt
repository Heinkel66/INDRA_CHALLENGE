package com.aquarius.indra_challenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquarius.indra_challenge.data.UserEntity
import com.aquarius.indra_challenge.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    val loginSucess = MutableLiveData<Boolean>()

    init {
        createDefaultUser()
    }

    fun authenticate(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUser(username, password)
            loginSucess.postValue(user != null)
        }
    }

    private fun createDefaultUser() {
        viewModelScope.launch {
            repository.insertUser(
                UserEntity(
                    username = "Admin",
                    password = "Password*123",
                    enable = true
                )
            )
        }
    }
}