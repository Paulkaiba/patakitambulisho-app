package com.example.patakitambulisho.app.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.util.Result
import kotlinx.coroutines.launch

class OtpVerificationViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _verifyOtpResult = MutableLiveData<Result<OtpVerifyResponse>>()
    val verifyOtpResult: LiveData<Result<OtpVerifyResponse>> = _verifyOtpResult

    private val _resendOtpResult = MutableLiveData<Result<DefaultResponse>>()
    val resendOtpResult: LiveData<Result<DefaultResponse>> = _resendOtpResult

    fun verifyOtp(email: String, otp: String) {
        _verifyOtpResult.value = Result.Loading
        viewModelScope.launch {
            try {
                val response = repository.verifyOtp(email, otp)
                _verifyOtpResult.value = Result.Success(response)
            } catch (e: Exception) {
                _verifyOtpResult.value = Result.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun resendOtp(email: String) {
        _resendOtpResult.value = Result.Loading
        viewModelScope.launch {
            try {
                val response = repository.resendOtp(email)
                _resendOtpResult.value = Result.Success(response)
            } catch (e: Exception) {
                _resendOtpResult.value = Result.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
