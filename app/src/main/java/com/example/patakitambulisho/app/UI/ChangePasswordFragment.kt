package com.example.patakitambulisho.app.UI

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
import com.example.patakitambulisho.app.UI.MainActivity  // Add this import
class ChangePasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        // Remove the "id =" part - just pass the ID directly
        val changePasswordButton = view.findViewById<Button>(R.id.changePasswordButton)
        val backToLoginText = view.findViewById<TextView>(R.id.backToLoginText)

        // Change Password Button Click
        changePasswordButton.setOnClickListener {
            // Also remove "id =" here
            val newPassword = view.findViewById<TextInputEditText>(R.id.newPasswordEditText).text.toString()
            val confirmPassword = view.findViewById<TextInputEditText>(R.id.confirmPasswordEditText).text.toString()

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Password changed successfully!", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).goBackToLogin()
            }
        }

        // Back to Login Click
        backToLoginText.setOnClickListener {
            (activity as MainActivity).goBackToLogin()
        }

        return view
    }
}