package com.example.patakitambulisho.app.UI
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.patakitambulisho.R
import com.google.android.material.textfield.TextInputEditText
class ForgotPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        val resetButton = view.findViewById<Button>(R.id.resetButton)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        // Reset Button Click
        resetButton.setOnClickListener {
            val email = view.findViewById<TextInputEditText>(R.id.emailEditText).text.toString()
            val phone = view.findViewById<TextInputEditText>(R.id.phoneEditText).text.toString()

            if (email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Simulate verification - matches your PHP logic
                (activity as MainActivity).navigateToChangePassword()
            }
        }

        // Login Button Click
        loginButton.setOnClickListener {
            (activity as MainActivity).goBackToLogin()
        }

        return view
    }
}