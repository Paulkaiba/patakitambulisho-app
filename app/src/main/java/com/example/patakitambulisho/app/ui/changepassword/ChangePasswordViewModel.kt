package com.example.patakitambulisho.app.ui.changepassword

import android.util.Log
import androidx.lifecycle.*
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.data.model.ChangePasswordResponse
import com.example.patakitambulisho.app.data.model.ResetPasswordRequest
import com.example.patakitambulisho.app.util.Result
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _result = MutableLiveData<Result<ChangePasswordResponse>>()
    val result: LiveData<Result<ChangePasswordResponse>> = _result

    fun changePassword(email: String, newPass: String, confirmPass: String) {
        _result.value = Result.Loading

        viewModelScope.launch {
            try {
                val request = ResetPasswordRequest(
                    email = email,
                    new_password = newPass,
                    confirm_password = confirmPass
                )

                val response = repository.resetPassword(request) // ChangePasswordResponse

                // Wrap response in Result
                if (response.success) {
                    Log.e("RESET_PASSWORD_SUCCESS", "Response: $response")
                    _result.value = Result.Success(response) // ✅ Success wrapper
                } else {
                    Log.e("RESET_PASSWORD_ERROR", "Error: ${response.message}")
                    _result.value = Result.Error(response.message) // ✅ Error wrapper
                }

            } catch (e: Exception) {
                Log.e("RESET_PASSWORD_EXCEPTION", "Exception occurred", e)
                _result.value = Result.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}




