package com.example.brainwest_android.ui.community.chating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentChatingCommunityBinding
import com.example.brainwest_android.databinding.FragmentChatingConsultationBinding

class ChatingCommunityFragment : Fragment() {
    lateinit var binding: FragmentChatingCommunityBinding

    private val viewModel: ChatingCommunityViewModel by viewModels {
        ChatingCommunityViewModelFactory(CommunityRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatingCommunityBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        showData()

        return binding.root
    }
    fun showData() {
        val id = requireActivity().intent.getIntExtra("id", 0)
        viewModel.getCommunityById(id)
        viewModel.result.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.layoutChat.visibility = View.GONE
                }
                is State.Success -> {
                    binding.tvName.text = state.data.name
                    binding.tvMember.text = "231 Members"
                    binding.pbLoading.visibility = View.GONE
                    binding.layoutChat.visibility = View.VISIBLE
                }
                is State.Error -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.layoutChat.visibility = View.GONE
                }
            }
        }
    }
}