package com.example.patakitambulisho.app.data.repository

import android.util.Log
import com.example.patakitambulisho.app.data.api.RetrofitClient
import com.example.patakitambulisho.app.data.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AuthRepository {

    suspend fun login(emailcont: String, password: String): LoginResponse {
        return withContext(Dispatchers.IO) {
            try {
                // Make Retrofit synchronous call
                val call = RetrofitClient.apiService.login(emailcont, password)
                val response = call.execute()

                Log.d("AUTH_REPO", "Response code: ${response.code()}, body: ${response.body()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        body
                    } else {
                        LoginResponse(false, "No response body")
                    }
                } else {
                    // Log HTTP error
                    val errorBody = response.errorBody()?.string()
                    Log.e("AUTH_REPO", "HTTP error: $errorBody")
                    LoginResponse(false, "HTTP error: ${response.code()}")
                }

            } catch (e: IOException) {
                Log.e("AUTH_REPO", "Network error", e)
                LoginResponse(false, "Network error: ${e.localizedMessage}")
            } catch (e: HttpException) {
                Log.e("AUTH_REPO", "HTTP exception", e)
                LoginResponse(false, "HTTP exception: ${e.localizedMessage}")
            } catch (e: Exception) {
                Log.e("AUTH_REPO", "Unknown error", e)
                LoginResponse(false, "Unknown error: ${e.localizedMessage}")
            }
        }
    }
}
