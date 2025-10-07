package com.example.brainwest_android.ui.consultation.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brainwest_android.R
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.data.repository.ChatConsultationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentHistoryConsultationBinding
import com.example.brainwest_android.ui.adapter.HistoryConsultationAdapter

class HistoryConsultationFragment : Fragment() {
    lateinit var binding: FragmentHistoryConsultationBinding

    private val viewModel: HistoryConsultationViewModel by viewModels {
        HistoryConsultationViewModelFactory(ChatConsultationRepository(requireContext()))
    }

    lateinit var adapter: HistoryConsultationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryConsultationBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        adapter = HistoryConsultationAdapter { history ->
            val bundle = Bundle().apply {
                putInt("doctor_id", history.doctor.id)
                putInt("doctor_user_id", history.doctor.user.id!!)
            }
            findNavController().navigate(R.id.action_historyFragment_to_chatingFragment, bundle)
        }

        showData()

        return binding.root
    }

    fun showData() {
        viewModel.getHistory()
        viewModel.historyResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.rvHistory.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    adapter.setData(state.data)
                    binding.rvHistory.adapter = adapter
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }
                is State.Error -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

}