package com.example.brainwest_android.ui.rehabilitation.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.GeneralPref
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.data.repository.RehabilitationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentSaveRehabilitationFragmentBinding
import com.example.brainwest_android.ui.adapter.RehabilitationAdapter
import com.example.brainwest_android.utils.Helper

class SaveRehabilitationFragmentFragment : Fragment() {
    lateinit var binding: FragmentSaveRehabilitationFragmentBinding

    private val viewModel: SaveRehabilitationViewModel by viewModels {
        SaveRehabilitationViewModelFactory(RehabilitationRepository())
    }

    lateinit var adapter: RehabilitationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSaveRehabilitationFragmentBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        adapter = RehabilitationAdapter {rehab ->
            val bundle = Bundle().apply {
                putInt("id", rehab.id)
            }
            findNavController().navigate(R.id.action_saveFragment_to_detailFragment, bundle)
        }
        showData()

        return binding.root
    }

    fun showData() {
        viewModel.send(GeneralPref(requireContext()).getRehabId())
        viewModel.result.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoadingBtnPrint.visibility = View.VISIBLE
                    binding.rvRehabilitation.visibility = View.GONE
                }
                is State.Success -> {
                    binding.pbLoadingBtnPrint.visibility = View.GONE
                    binding.rvRehabilitation.visibility = View.VISIBLE

                    adapter.setData(state.data)
                    binding.rvRehabilitation.adapter = adapter
                }
                is State.Error -> {
                    binding.pbLoadingBtnPrint.visibility = View.VISIBLE
                    binding.rvRehabilitation.visibility = View.GONE
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }
    }
}