package com.example.patakitambulisho.app.UIfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.patakitambulisho.R
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.ui.login.LoginViewModel
import com.example.patakitambulisho.app.ui.login.LoginViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var usernameField: TextInputEditText
    private lateinit var passwordField: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize views
        usernameField = view.findViewById(R.id.usernameEditText)
        passwordField = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)
        progressBar = view.findViewById(R.id.progressBarLogin)

        // Setup ViewModel
        val repository = AuthRepository()
        val factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            loginButton.isEnabled = !isLoading
        }

        // Observe login result
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            Log.d("LOGIN_RESPONSE", "Response: $result")
            if (result.success) {
                Toast.makeText(requireContext(), "✅ ${result.message}", Toast.LENGTH_LONG).show()

                // ✅ Navigate to DashboardFragment
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DashboardFragment())
                    .addToBackStack(null)  // allows back navigation
                    .commit()

            } else {
                Toast.makeText(requireContext(), "❌ ${result.message}", Toast.LENGTH_LONG).show()
            }
        }


        // Login button click
        loginButton.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            Log.d("LOGIN_INPUT", "Trying login with: $username / $password")

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter both fields", Toast.LENGTH_SHORT).show()
            } else {
                // Call ViewModel login
                viewModel.login(username, password)
            }
        }

        return view
    }
}
