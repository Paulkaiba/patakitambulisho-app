package com.example.patakitambulisho.app.ui.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patakitambulisho.app.data.model.ForgotPasswordResponse
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.util.Result
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _forgotPasswordResult = MutableLiveData<Result<ForgotPasswordResponse>>()
    val forgotPasswordResult: LiveData<Result<ForgotPasswordResponse>> = _forgotPasswordResult

    fun sendResetEmail(email: String, phone: String) {
        _forgotPasswordResult.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = repository.forgotPassword(email, phone)

                if (response.success) {
                    _forgotPasswordResult.value = Result.Success(response)
                } else {
                    _forgotPasswordResult.value = Result.Error(response.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _forgotPasswordResult.value = Result.Error(e.localizedMessage ?: "Network error")
            }
        }
    }
}
