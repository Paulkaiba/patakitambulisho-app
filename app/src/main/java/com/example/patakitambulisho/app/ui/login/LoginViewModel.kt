package com.example.patakitambulisho.app.ui.login

import androidx.lifecycle.*
import com.example.patakitambulisho.app.data.model.LoginResponse
import com.example.patakitambulisho.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    fun login(emailcont: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.login(emailcont, password)
            _loginResult.value = result
            _isLoading.value = false
        }
    }
}