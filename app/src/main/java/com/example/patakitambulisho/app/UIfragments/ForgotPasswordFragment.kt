package com.example.patakitambulisho.app.ui.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.patakitambulisho.R
import com.example.patakitambulisho.app.UIfragments.OtpVerificationFragment
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.util.Result

class ForgotPasswordFragment : Fragment() {

    private lateinit var viewModel: ForgotPasswordViewModel

    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var resetButton: Button
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        emailInput = view.findViewById(R.id.emailEditText)
        phoneInput = view.findViewById(R.id.phoneEditText)
        resetButton = view.findViewById(R.id.resetButton)
        loginButton = view.findViewById(R.id.loginButton)

        // Setup MVVM
        val repository = AuthRepository()
        val factory = ForgotPasswordViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ForgotPasswordViewModel::class.java]

        // Handle Reset Button Click
        resetButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()

            if (email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.sendResetEmail(email, phone)
        }

        // Handle Login Button Click → goes back to Login Fragment
        loginButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Observe ViewModel Response
        viewModel.forgotPasswordResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(requireContext(), "Sending email… Please wait", Toast.LENGTH_SHORT).show()
                }

                is Result.Success -> {
                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()

                    // Use email from server response (safer than reading UI)
                    val returnedEmail = result.data.email ?: emailInput.text.toString().trim()
                    if (returnedEmail.isBlank()) {
                        // fallback if neither is available
                        Toast.makeText(requireContext(), "No email available to proceed", Toast.LENGTH_LONG).show()
                        return@observe
                    }

                    // Prepare OTP fragment with email argument
                    val bundle = Bundle().apply { putString("email", returnedEmail) }
                    val otpFragment = OtpVerificationFragment()
                    otpFragment.arguments = bundle

                    // Navigate using FragmentManager (ensure container ID is correct)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, otpFragment) // <-- confirm this ID
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
