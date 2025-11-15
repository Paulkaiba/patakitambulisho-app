package com.example.patakitambulisho.app.UIfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.patakitambulisho.R
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.ui.registerotp.RegisterOtpViewModel
import com.example.patakitambulisho.app.ui.registerotp.RegisterOtpViewModelFactory
import com.example.patakitambulisho.app.util.Result

class RegisterOtpFragment : Fragment() {

    private lateinit var viewModel: RegisterOtpViewModel
    private lateinit var otpInput: EditText
    private lateinit var verifyButton: Button
    private lateinit var resendText: TextView
    private lateinit var backToLoginText: TextView

    private var email: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve email from register fragment
        email = arguments?.getString("email") ?: ""

        otpInput = view.findViewById(R.id.otpEditText)
        verifyButton = view.findViewById(R.id.verifyOtpButton)
        resendText = view.findViewById(R.id.resendOtpText)
        backToLoginText = view.findViewById(R.id.backToLoginText)

        // Init ViewModel
        val repository = AuthRepository()
        val factory = RegisterOtpViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RegisterOtpViewModel::class.java]

        // VERIFY OTP BUTTON
        verifyButton.setOnClickListener {
            val otp = otpInput.text.toString().trim()
            if (otp.isEmpty()) {
                Toast.makeText(requireContext(), "Enter OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            verifyButton.isEnabled = false
            viewModel.verifyOtp(email, otp)
        }

        // RESEND OTP
        resendText.setOnClickListener {
            viewModel.resendOtp(email)
        }

        // GO BACK TO LOGIN
        backToLoginText.setOnClickListener {
            (activity as? MainActivity)?.goBackToLogin()
        }

        // OBSERVE VERIFY OTP RESULT
        viewModel.verifyOtpResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(requireContext(), "Verifying OTP…", Toast.LENGTH_SHORT).show()
                }

                is Result.Success -> {

                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DashboardFragment())
                    .commit()
                }

                is Result.Error -> {
                    // show server error message or generic error
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                    verifyButton.isEnabled = true  // re-enable so user can retry
                }
            }
        }

        // OBSERVE RESEND OTP RESULT
        viewModel.resendOtpResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(requireContext(), "Resending OTP…", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
