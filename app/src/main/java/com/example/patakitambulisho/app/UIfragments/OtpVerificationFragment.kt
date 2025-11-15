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
import com.example.patakitambulisho.app.ui.otp.OtpVerificationViewModelFactory
import com.example.patakitambulisho.app.data.repository.AuthRepository
import com.example.patakitambulisho.app.ui.otp.OtpVerificationViewModel
import com.example.patakitambulisho.app.util.Result

class OtpVerificationFragment : Fragment() {

    private lateinit var viewModel: OtpVerificationViewModel
    private lateinit var otpInput: EditText
    private lateinit var verifyButton: Button
    private lateinit var resendText: TextView
    private lateinit var backToLoginText: TextView

    private var email: String = "" // pass email from previous fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_otp_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // STEP 3: RECEIVE EMAIL FROM BUNDLE
        email = arguments?.getString("email") ?: ""

        otpInput = view.findViewById(R.id.otpEditText)
        verifyButton = view.findViewById(R.id.verifyOtpButton)
        resendText = view.findViewById(R.id.resendOtpText)
        backToLoginText = view.findViewById(R.id.backToLoginText)

        val repository = AuthRepository()
        val factory = OtpVerificationViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[OtpVerificationViewModel::class.java]

        verifyButton.setOnClickListener {
            val otp = otpInput.text.toString().trim()
            if (otp.isEmpty()) {
                Toast.makeText(requireContext(), "Enter OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.verifyOtp(email, otp)
        }

        resendText.setOnClickListener {
            viewModel.resendOtp(email)
        }

        // Back to login
        backToLoginText.setOnClickListener {
            // Use FragmentManager instead of NavController
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Observe verify OTP
        viewModel.verifyOtpResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> Toast.makeText(requireContext(), "Verifying OTP…", Toast.LENGTH_SHORT).show()

                is Result.Success -> {
                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()

                    val bundle = Bundle()
                    bundle.putString("email", email)

                    val changePasswordFragment = ChangePasswordFragment()
                    changePasswordFragment.arguments = bundle

                    // Clear all previous fragments including OTP fragment
                    val fragmentManager = requireActivity().supportFragmentManager
                    fragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)

                    // Replace fragment without adding to back stack
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, changePasswordFragment)
                        .commit()
                }

                is Result.Error -> Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
            }
        }


        // Observe resend OTP
        viewModel.resendOtpResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> Toast.makeText(requireContext(), "Resending OTP…", Toast.LENGTH_SHORT).show()
                is Result.Success -> Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()
                is Result.Error -> Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
