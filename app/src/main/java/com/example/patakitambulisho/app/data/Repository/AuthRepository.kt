package com.example.patakitambulisho.app.data.repository

import com.example.patakitambulisho.app.data.api.RetrofitClient
import com.example.patakitambulisho.app.data.model.LoginResponse

class AuthRepository {

    suspend fun login(emailcont: String, password: String): LoginResponse {
        return try {
            val response = RetrofitClient.apiService.login(emailcont, password).execute()

            response.body() ?: LoginResponse(
                success = false,
                message = "No response from server"
            )

        } catch (e: Exception) {
            LoginResponse(
                success = false,
                message = e.localizedMessage ?: "Network error"
            )
        }
    }
}
