package com.example.patakitambulisho.app.network


import com.example.patakitambulisho.app.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("username") username: String,       // or "email" depending on your API
        @Field("password") password: String
    ): Response<LoginResponse>
}
