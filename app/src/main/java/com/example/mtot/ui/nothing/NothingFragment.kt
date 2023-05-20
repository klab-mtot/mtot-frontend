package com.example.mtot.ui.nothing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mtot.databinding.FragmentNothingBinding

class NothingFragment : Fragment() {

    lateinit var binding: FragmentNothingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNothingBinding.inflate(inflater, container, false)

        return binding.root
    }
}