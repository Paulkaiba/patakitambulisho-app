package com.example.patakitambulisho.app.UIfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.patakitambulisho.R

class OtpVerificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp_verification, container, false)
    }

    companion object {
        fun newInstance(): OtpVerificationFragment {
            return OtpVerificationFragment()
        }
    }
}