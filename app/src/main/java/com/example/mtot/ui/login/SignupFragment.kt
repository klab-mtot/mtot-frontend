package com.example.mtot.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import com.example.mtot.R
import com.example.mtot.StartActivity
import com.example.mtot.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    lateinit var binding : FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)


        binding.button.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.login_frm, LoginFragment()).commit()
        }

        binding.ivSignupBack.setOnClickListener {
            val i = Intent(requireContext(), StartActivity::class.java)
            startActivity(i)
        }

        return binding.root
    }

}