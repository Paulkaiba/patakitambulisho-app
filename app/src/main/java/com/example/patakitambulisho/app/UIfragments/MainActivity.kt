package com.example.patakitambulisho.app.UIfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.patakitambulisho.R
import com.example.patakitambulisho.app.ui.forgot.ForgotPasswordFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply your Material theme
        setTheme(R.style.Theme_Patakitambulisho)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the LoginFragment only if first launch (prevents overlapping fragments)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }

    // ✅ Navigation functions
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

    fun navigateToRegister() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    fun goBackToLogin() {
        // Clear the entire back stack
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)

        // Replace with LoginFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    // ✅ Navigate to DashboardFragment after successful login
    fun navigateToDashboard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DashboardFragment())
            .addToBackStack(null)
            .commit()
    }
}
