package com.example.patakitambulisho.app.UIfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.patakitambulisho.R
import com.example.patakitambulisho.app.data.model.RegisterRequest
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.util.Result
import com.example.patakitambulisho.app.ui.register.RegisterViewModel
import com.example.patakitambulisho.app.ui.register.RegisterViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var firstNameField: TextInputEditText
    private lateinit var lastNameField: TextInputEditText
    private lateinit var contactField: TextInputEditText
    private lateinit var emailField: TextInputEditText
    private lateinit var passwordField: TextInputEditText
    private lateinit var confirmPasswordField: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameField = view.findViewById(R.id.firstNameEditText)
        lastNameField = view.findViewById(R.id.lastNameEditText)
        contactField = view.findViewById(R.id.contactNumberEditText)
        emailField = view.findViewById(R.id.emailEditText)
        passwordField = view.findViewById(R.id.passwordEditText)
        confirmPasswordField = view.findViewById(R.id.repeatPasswordEditText)
        registerButton = view.findViewById(R.id.registerButton)
        loginButton = view.findViewById(R.id.loginButton)

        val repository = AuthRepository()
        val factory = RegisterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        // Register button click
        registerButton.setOnClickListener {
            val firstName = firstNameField.text.toString().trim()
            val lastName = lastNameField.text.toString().trim()
            val contact = contactField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || contact.isEmpty() ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
            ) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(firstName, lastName, contact, email, password)
            viewModel.registerUser(request)
        }

        // Login button click - go back to LoginFragment
        loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }

        // Observe API results
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> Toast.makeText(requireContext(), "Registering user...", Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()

                    // Open OTP screen and pass the email
                    val otpFragment = RegisterOtpFragment()
                    val bundle = Bundle()
                    bundle.putString("email", emailField.text.toString().trim())
                    otpFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, otpFragment)
                        .addToBackStack(null)
                        .commit()
                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
