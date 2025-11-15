package com.example.patakitambulisho.app.ui.registerotp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.patakitambulisho.app.data.repository.AuthRepository

class RegisterOtpViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterOtpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterOtpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
