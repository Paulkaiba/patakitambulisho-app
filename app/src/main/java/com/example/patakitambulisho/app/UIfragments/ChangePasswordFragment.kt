package com.example.patakitambulisho.app.UIfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.example.patakitambulisho.R
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.ui.changepassword.ChangePasswordViewModel
import com.example.patakitambulisho.app.ui.changepassword.ChangePasswordViewModelFactory
import com.example.patakitambulisho.app.util.Result
import androidx.lifecycle.ViewModelProvider

class ChangePasswordFragment : Fragment() {

    private lateinit var viewModel: ChangePasswordViewModel
    private var email: String = ""   // receive this from OTP fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = arguments?.getString("email") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        val newPassField = view.findViewById<TextInputEditText>(R.id.newPasswordEditText)
        val confirmPassField = view.findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val btnChange = view.findViewById<Button>(R.id.changePasswordButton)
        val backToLogin = view.findViewById<TextView>(R.id.backToLoginText)

        val repository = AuthRepository()
        val factory = ChangePasswordViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ChangePasswordViewModel::class.java]

        btnChange.setOnClickListener {
            val newPass = newPassField.text.toString()
            val confirmPass = confirmPassField.text.toString()

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(requireContext(), "All fields required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPass != confirmPass) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.changePassword(email, newPass, confirmPass)
        }

        backToLogin.setOnClickListener {
            (activity as MainActivity).goBackToLogin()
        }

        // Observe API results
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> Toast.makeText(requireContext(), "Updating password...", Toast.LENGTH_SHORT).show()

                is Result.Success -> {
                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()
                    (activity as MainActivity).goBackToLogin()
                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }
}
