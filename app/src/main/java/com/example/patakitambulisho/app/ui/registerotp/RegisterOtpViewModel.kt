package com.example.patakitambulisho.app.ui.registerotp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patakitambulisho.app.data.model.AuthResponse
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.util.Result
import kotlinx.coroutines.launch

class RegisterOtpViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _verifyOtpResult = MutableLiveData<Result<AuthResponse>>()
    val verifyOtpResult: LiveData<Result<AuthResponse>> get() = _verifyOtpResult

    private val _resendOtpResult = MutableLiveData<Result<AuthResponse>>()
    val resendOtpResult: LiveData<Result<AuthResponse>> get() = _resendOtpResult

    fun verifyOtp(email: String, otp: String) {
        _verifyOtpResult.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = repository.verifyRegistrationOtp(email, otp)

                // IMPORTANT: check the API's 'success' boolean
                if (response.success) {
                    _verifyOtpResult.value = Result.Success(response)
                } else {
                    // send the server message as an Error so fragment won't treat it as success
                    _verifyOtpResult.value = Result.Error(response.message)
                }
            } catch (e: Exception) {
                _verifyOtpResult.value = Result.Error(e.localizedMessage ?: "Error verifying OTP")
            }
        }
    }

    fun resendOtp(email: String) {
        _resendOtpResult.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = repository.resendRegistrationOtp(email)

                if (response.success) {
                    _resendOtpResult.value = Result.Success(response)
                } else {
                    _resendOtpResult.value = Result.Error(response.message)
                }
            } catch (e: Exception) {
                _resendOtpResult.value = Result.Error(e.localizedMessage ?: "Error resending OTP")
            }
        }
    }
}
