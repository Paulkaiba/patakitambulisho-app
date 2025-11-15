package com.example.patakitambulisho.app.data.repository


import android.util.Log
import com.example.patakitambulisho.app.data.api.RetrofitClient
import com.example.patakitambulisho.app.data.model.*
import com.example.patakitambulisho.app.util.Result
import retrofit2.HttpException
import java.io.IOException

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

    suspend fun verifyOtp(email: String, otp: String): Result<OtpVerifyResponse> {
        return try {
            // Call the Retrofit API using the existing client
            val response = RetrofitClient.apiService.verifyOtp(email, otp)

            // Check success from backend
            if (response.success) {
                Result.Success(response)
            } else {
                Result.Error(response.message)
            }
        } catch (e: IOException) {
            // Network or conversion error
            Result.Error("Network error. Please try again.")
        } catch (e: HttpException) {
            // HTTP error
            Result.Error("Server error. Please try again.")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    suspend fun resendOtp(email: String): Result<OtpVerifyResponse> {
        return try {
            val response = RetrofitClient.apiService.resendOtp(email)
            if (response.success) {
                Result.Success(response)
            } else {
                Result.Error(response.message)
            }
        } catch (e: IOException) {
            Result.Error("Network error. Please try again.")
        } catch (e: HttpException) {
            Result.Error("Server error. Please try again.")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ChangePasswordResponse {
        return try {
            Log.d("RESET_REQUEST", "Sending JSON: $request")
            val response = RetrofitClient.apiService.resetPassword(request)
            Log.d("RESET_RESPONSE", "Received: $response")
            response
        } catch (e: Exception) {
            Log.e("RESET_ERROR", "Exception:", e)
            ChangePasswordResponse(false, "Network error: ${e.localizedMessage}")
        }
    }

    suspend fun registerUser(request: RegisterRequest): RegisterResponse {
        return try {
            val response = RetrofitClient.apiService.registerUser(request)
            response
        } catch (e: Exception) {
            RegisterResponse(false, "Network error: ${e.localizedMessage}")
        }
    }

    // VERIFY OTP (registration)
    // VERIFY OTP (registration)
    suspend fun verifyRegistrationOtp(email: String, otp: String): AuthResponse {
        return try {
            RetrofitClient.apiService.verifyRegistrationOtp(OtpRequest(email, otp))
        } catch (e: Exception) {
            AuthResponse(false, e.localizedMessage ?: "Network error")
        }
    }


    // RESEND OTP (registration)
    suspend fun resendRegistrationOtp(email: String): AuthResponse {
        return try {
            val response = RetrofitClient.apiService.resendRegistrationOtp(
                ResendOtpRequest(email)
            )

            response
        } catch (e: Exception) {
            AuthResponse(false, "Network error: ${e.localizedMessage}")
        }
    }


}
