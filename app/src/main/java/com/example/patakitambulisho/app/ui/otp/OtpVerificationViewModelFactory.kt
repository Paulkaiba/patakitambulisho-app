package com.example.patakitambulisho.app.ui.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.patakitambulisho.app.data.repository.AuthRepository

class OtpVerificationViewModelFactory(private val repository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OtpVerificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OtpVerificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
