package com.example.brainwest_android.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentProfileBinding
import com.example.brainwest_android.ui.parent.AuthActivity
import com.example.brainwest_android.ui.parent.MainActivity

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        binding.logout.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
            requireActivity().finish()
        }

        return binding.root
    }
}