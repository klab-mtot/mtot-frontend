package com.example.mtot.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mtot.R
import com.example.mtot.StartActivity
import com.example.mtot.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.login_frm, SetProfileFragment()).commit()
        }

        binding.textView3.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.login_frm, SignupFragment()).commit()
        }

        binding.ivLoginBack.setOnClickListener {
            val i = Intent(requireContext(), StartActivity::class.java)
            startActivity(i)
        }


        return binding.root
    }

}