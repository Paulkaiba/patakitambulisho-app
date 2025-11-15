package com.example.patakitambulisho.app.data.model

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val contactNumber: String,
    val email: String,
    val password: String
)
