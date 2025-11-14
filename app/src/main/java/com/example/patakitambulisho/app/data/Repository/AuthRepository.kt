package com.example.patakitambulisho.app.data.repository

import com.example.patakitambulisho.app.data.api.RetrofitClient
import com.example.patakitambulisho.app.data.model.*

class AuthRepository {

    suspend fun login(email: String, password: String): LoginResponse {
        return try {
            RetrofitClient.apiService.login(email, password)
        } catch (e: Exception) {
            LoginResponse(false, "Network error: ${e.localizedMessage}")
        }
    }

    suspend fun forgotPassword(email: String, phone: String): ForgotPasswordResponse {
        return try {
            RetrofitClient.apiService.forgotPassword(email, phone)
        } catch (e: Exception) {
            ForgotPasswordResponse(false, "Network error: ${e.localizedMessage}")
        }
    }

}
