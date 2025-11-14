package com.example.brainwest_android.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.AuthRepository
import com.example.brainwest_android.databinding.FragmentRegisterBinding
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.data.state.State

class RegisterFragment : Fragment() {
   lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(AuthRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.root.setOnClickListener {
            Helper.clearFocusOnEdtText(requireContext(), binding.etFullname)
            Helper.clearFocusOnEdtText(requireContext(), binding.etPassword)
            Helper.clearFocusOnEdtText(requireContext(), binding.etUsername)
        }

        handleRegister()

        return binding.root
    }

    fun handleRegister() {
        binding.apply {
            btnRegister.setOnClickListener {
                val username = etUsername.text.toString()
                val fullname = etFullname.text.toString()
                val password = etPassword.text.toString()
                viewModel.register(username, fullname, password)
            }
        }
        viewModel.registerResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.btnRegister.visibility = View.GONE
                }
                is State.Success -> {
                    Helper.showSuccessToast(requireContext(), state.message)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    binding.pbLoading.visibility = View.GONE
                    binding.btnRegister.visibility = View.VISIBLE
                }
                is State.Error -> {
                    Helper.showErrorToast(requireContext(), state.message)
                    binding.pbLoading.visibility = View.GONE
                    binding.btnRegister.visibility = View.VISIBLE
                    binding.etFullname.text.clear()
                    binding.etUsername.text.clear()
                    binding.etPassword.text.clear()
                }
            }
        }
    }
}