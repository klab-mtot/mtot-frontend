package com.example.mtot.ui.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mtot.databinding.FragmentMapBinding
import com.example.mtot.databinding.FragmentSocialBinding

class SocialFragment : Fragment() {

    lateinit var binding: FragmentSocialBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialBinding.inflate(inflater, container, false)

        return binding.root
    }
}