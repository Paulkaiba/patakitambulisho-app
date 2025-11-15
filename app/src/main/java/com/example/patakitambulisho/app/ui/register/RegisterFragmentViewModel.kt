package com.example.patakitambulisho.app.ui.register

import androidx.lifecycle.*
import com.example.patakitambulisho.app.data.model.RegisterRequest
import com.example.patakitambulisho.app.data.model.RegisterResponse
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.util.Result
import kotlinx.coroutines.launch
import android.util.Log

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _result = MutableLiveData<Result<RegisterResponse>>()
    val result: LiveData<Result<RegisterResponse>> = _result

    fun registerUser(request: RegisterRequest) {
        _result.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = repository.registerUser(request)
                if (response.success) {
                    Log.d("REGISTER_SUCCESS", "Response: $response")
                    _result.value = Result.Success(response)
                } else {
                    Log.d("REGISTER_ERROR", "Response: $response")
                    _result.value = Result.Error(response.message)
                }
            } catch (e: Exception) {
                Log.e("REGISTER_EXCEPTION", "Exception", e)
                _result.value = Result.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
