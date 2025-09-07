package com.example.brainwest_android.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.databinding.FragmentEventBinding
import com.example.brainwest_android.ui.adapter.EventAdapter
import com.example.brainwest_android.ui.education.EducationViewModel
import com.example.brainwest_android.ui.education.EducationViewModelFactory
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State

class EventFragment : Fragment() {
    lateinit var binding: FragmentEventBinding

    private val viewModel: EventViewModel by viewModels {
        EventViewModelfactory(EventRepository())
    }

    lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEventBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        binding.btnTicket.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragment_to_myEventTransactionFragment)
        }

        eventAdapter = EventAdapter {event ->
            val bundle = Bundle().apply {
                putInt("id", event.id!!)
                putBoolean("is", false)
            }
            findNavController().navigate(R.id.action_eventFragment_to_eventDetailFragment, bundle)
        }

        showData()
        return binding.root
    }

    fun showData() {
        viewModel.getAllEvent(requireContext())
        viewModel.getAllEventResult.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {
                    binding.rvEvent.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    eventAdapter.setData(state.data)
                    binding.rvEvent.adapter = eventAdapter
                    binding.rvEvent.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }
                is State.Error -> {
                    Helper.showErrorToast(requireContext(), state.message)
                    findNavController().popBackStack()
                }
            }
        }
    }
}