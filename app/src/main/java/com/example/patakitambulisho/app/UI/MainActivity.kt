package com.example.patakitambulisho.app.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.patakitambulisho.R
import com.example.patakitambulisho.app.UI.ChangePasswordFragment
import com.example.patakitambulisho.app.UI.ForgotPasswordFragment
import com.example.patakitambulisho.app.UI.LoginFragment
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Force Material theme programmatically
        setTheme(R.style.Theme_Patakitambulisho)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load login fragment as default
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    fun navigateToForgotPassword() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ForgotPasswordFragment())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToChangePassword() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ChangePasswordFragment())
            .addToBackStack(null)
            .commit()
    }

    fun goBackToLogin() {
        supportFragmentManager.popBackStack()
    }
}