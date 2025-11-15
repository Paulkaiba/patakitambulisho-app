package com.example.patakitambulisho.app.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.data.model.OtpVerifyResponse
import com.example.patakitambulisho.app.util.Result
import kotlinx.coroutines.launch

class OtpVerificationViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _verifyOtpResult = MutableLiveData<Result<OtpVerifyResponse>>()
    val verifyOtpResult: LiveData<Result<OtpVerifyResponse>> = _verifyOtpResult

    private val _resendOtpResult = MutableLiveData<Result<OtpVerifyResponse>>() // Reuse OtpVerifyResponse
    val resendOtpResult: LiveData<Result<OtpVerifyResponse>> = _resendOtpResult

    fun verifyOtp(email: String, otp: String) {
        _verifyOtpResult.value = Result.Loading
        viewModelScope.launch {
            val result = repository.verifyOtp(email, otp)
            _verifyOtpResult.value = result
        }
    }

    fun resendOtp(email: String) {
        _resendOtpResult.value = Result.Loading
        viewModelScope.launch {
            val result = repository.resendOtp(email) // This needs to be implemented in AuthRepository
            _resendOtpResult.value = result
        }
    }
}
