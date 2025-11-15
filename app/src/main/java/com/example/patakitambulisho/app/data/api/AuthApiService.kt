package com.example.patakitambulisho.app.data.api


import com.example.patakitambulisho.app.data.model.*
import retrofit2.http.*
import retrofit2.Response

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
    @FormUrlEncoded
    @POST("twofactor.php")
    suspend fun verifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): OtpVerifyResponse

    // RESEND OTP
    @FormUrlEncoded
    @POST("resend-otp.php")
    suspend fun resendOtp(
        @Field("email") email: String
    ): OtpVerifyResponse

    @POST("reset-password.php")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): ChangePasswordResponse

    @POST("register.php")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse

    @POST("twofactor.php")
    suspend fun verifyRegistrationOtp(
        @Body request: OtpRequest
    ): AuthResponse

    @POST("resend-otp.php")
    suspend fun resendRegistrationOtp(
        @Body request: ResendOtpRequest
    ): AuthResponse


}
