
package com.example.patakitambulisho.app.data.api

import com.example.patakitambulisho.app.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("emailcont") emailOrPhone: String,
        @Field("password") password: String
    ): Call<LoginResponse>


}