package com.example.brainwest_android.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.AuthRepository
import com.example.brainwest_android.databinding.FragmentLoginBinding
import com.example.brainwest_android.ui.auth.register.RegisterViewModel
import com.example.brainwest_android.ui.auth.register.RegisterViewModelFactory
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(AuthRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        handleLogin()

        binding.etUsername.setText("pandu")
        binding.etPassword.setText("pandu")

        return binding.root
    }

    fun handleLogin() {
        binding.apply {
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                viewModel.login(username, password, requireContext())

            }
            viewModel.loginResult.observe(viewLifecycleOwner) {state ->
                when (state) {
                    is State.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                        binding.btnLogin.visibility = View.GONE
                    }
                    is State.Success -> {
                        Helper.showSuccessToast(requireContext(), state.message)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        binding.pbLoading.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE
                    }
                    is State.Error -> {
                        Helper.showErrorToast(requireContext(), state.message)
                        binding.pbLoading.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.etUsername.text.clear()
                        binding.etPassword.text.clear()
                    }
                }
            }
        }
    }


}