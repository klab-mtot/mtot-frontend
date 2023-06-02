package com.example.mtot.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mtot.MainActivity
import com.example.mtot.R
import com.example.mtot.StartActivity
import com.example.mtot.databinding.FragmentSetProfileBinding


class SetProfileFragment : Fragment() {
    lateinit var binding : FragmentSetProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetProfileBinding.inflate(inflater, container, false)

        binding.next.setOnClickListener {
            val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
        }
        binding.previous.setOnClickListener {
            val i = Intent(requireContext(), StartActivity::class.java)
            startActivity(i)
        }
        return binding.root
    }

}