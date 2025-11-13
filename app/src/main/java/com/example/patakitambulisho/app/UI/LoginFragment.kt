package com.example.patakitambulisho.app.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.patakitambulisho.R
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val forgotPasswordText = view.findViewById<TextView>(R.id.forgotPasswordText)
        val backHomeText = view.findViewById<TextView>(R.id.backHomeText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        // Forgot Password Click
        forgotPasswordText.setOnClickListener {
            (activity as MainActivity).navigateToForgotPassword()
        }

        // Back Home Click
        backHomeText.setOnClickListener {
            Toast.makeText(requireContext(), "Back Home clicked", Toast.LENGTH_SHORT).show()
        }

        // Login Button Click
        loginButton.setOnClickListener {
            val username = view.findViewById<TextInputEditText>(R.id.usernameEditText).text.toString()
            val password = view.findViewById<TextInputEditText>(R.id.passwordEditText).text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Login attempt for: $username", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}