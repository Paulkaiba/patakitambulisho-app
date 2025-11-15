package com.example.patakitambulisho.app.UIfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.patakitambulisho.R
import android.widget.ImageView


class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backIcon = view.findViewById<ImageView>(R.id.backIcon)
        backIcon.setOnClickListener {

            (activity as? MainActivity)?.goBackToLogin()
        }
    }

}
