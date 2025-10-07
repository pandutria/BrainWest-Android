package com.example.brainwest_android.ui.consultation.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.ConsultationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentListConsultationBinding
import com.example.brainwest_android.ui.adapter.ConsultationAdapter
import com.example.brainwest_android.utils.Helper

class ListConsultationFragment : Fragment() {
    lateinit var binding: FragmentListConsultationBinding

    private val viewModel: ListConsultationViewModel by viewModels {
        ListConsultationViewModelFactory(ConsultationRepository())
    }

    lateinit var consultationAdapter: ConsultationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListConsultationBinding.inflate(layoutInflater)

        consultationAdapter = ConsultationAdapter { doctor ->
            val bundle = Bundle().apply {
                putInt("doctor_id", doctor.id)
                putInt("doctor_user_id", doctor.user.id!!)
            }
            Log.d("DoctorDebug", "doctor.id=${doctor.id}, doctor.user.id=${doctor.user.id}")
            findNavController().navigate(R.id.action_listFragment_to_chatingFragment, bundle)
        }

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        binding.btnChat.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_historyFragment)
        }

        showData()

        return binding.root

    }

    fun showData() {
        viewModel.getDoctor()
        viewModel.getDoctorResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.rvDoctor.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    consultationAdapter.setData(state.data)
                    binding.rvDoctor.adapter = consultationAdapter
                    binding.rvDoctor.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }
                is State.Error -> {
                    Helper.showErrorToast(requireContext(), state.message)
                    Helper.showErrorLog(state.message)
                    requireActivity().finish()
                    requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
                }
            }
        }
    }
}