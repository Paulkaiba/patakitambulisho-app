package com.example.patakitambulisho.app.data.model

data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String,
    val email: String? = null
)
