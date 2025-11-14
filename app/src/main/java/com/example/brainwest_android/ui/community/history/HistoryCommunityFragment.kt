package com.example.brainwest_android.ui.community.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentHistoryCommunityBinding
import com.example.brainwest_android.ui.adapter.HistoryCommunityAdapter
import com.example.brainwest_android.ui.community.chating.ChatingCommunityFragment
import com.example.brainwest_android.ui.parent.CommunityActivity

class HistoryCommunityFragment : Fragment() {
    lateinit var binding: FragmentHistoryCommunityBinding

    private val viewModel: HistoryCommunityViewModel by viewModels {
        HistoryCommunityViewModelFactory(CommunityRepository())
    }

    lateinit var adapter: HistoryCommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryCommunityBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        adapter = HistoryCommunityAdapter { history ->
            val intent = Intent(requireContext(), CommunityActivity::class.java).apply {
                putExtra("id", history.group.id)
                putExtra("isDetail", true)
            }
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        showData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
    }

    fun showData() {
        viewModel.getHistoryMessage(requireContext())
        viewModel.result.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.GONE
                }
                is State.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvHistory.visibility = View.VISIBLE

                    adapter.setData(state.data)
                    binding.rvHistory.adapter = adapter
                }
                is State.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvHistory.visibility = View.VISIBLE
                    viewModel.getHistoryMessage(requireContext())
                }
            }
        }
    }
}