package com.example.patakitambulisho.app.data.model
data class ResetPasswordRequest(
    val email: String,
    val new_password: String,
    val confirm_password: String
)
