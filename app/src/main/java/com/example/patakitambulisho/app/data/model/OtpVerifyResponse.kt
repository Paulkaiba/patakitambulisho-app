package com.example.patakitambulisho.app.data.model

import com.google.gson.annotations.SerializedName

data class OtpVerifyResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String
)
