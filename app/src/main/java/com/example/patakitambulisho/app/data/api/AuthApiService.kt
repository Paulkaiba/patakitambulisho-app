package com.example.patakitambulisho.app.data.api

import com.example.patakitambulisho.app.data.model.*
import retrofit2.http.*

interface AuthApiService {

    // LOGIN
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("emailcont") email: String,
        @Field("password") password: String
    ): LoginResponse


    // FORGOT PASSWORD – sends email & OTP
    @FormUrlEncoded
    @POST("forget-password.php")
    suspend fun forgotPassword(
        @Field("email") email: String,
        @Field("mobilenumber") phone: String
    ): ForgotPasswordResponse


    // VERIFY OTP


}