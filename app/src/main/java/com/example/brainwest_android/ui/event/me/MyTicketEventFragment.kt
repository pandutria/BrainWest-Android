package com.example.brainwest_android.ui.event.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.databinding.FragmentMyTicketEventBinding
import com.example.brainwest_android.ui.adapter.EventAdapter
import com.example.brainwest_android.ui.adapter.MyEventAdapter
import com.example.brainwest_android.ui.education.detail.article.ArticleViewModel
import com.example.brainwest_android.ui.education.detail.article.ArticleViewModelFactory
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State

class MyTicketEventFragment : Fragment() {
    lateinit var binding: FragmentMyTicketEventBinding

    private val viewModel: MyTicketEventViewModel by viewModels {
        MyEventViewModelfactory(EventRepository())
    }

    lateinit var myEventAdapter: MyEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyTicketEventBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_myEventTransactionFragment_to_eventFragment)
        }

        myEventAdapter = MyEventAdapter {event ->
            val bundle = Bundle().apply {
                putInt("id", event.event!!.id!!)
                putBoolean("is", true)
            }
            findNavController().navigate(R.id.action_myEventTransactionFragment_to_eventDetailFragment, bundle)
        }

        showData()

        return binding.root
    }

    fun showData() {
        viewModel.getMyEvent(requireContext())
        viewModel.getMyEventResult.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {
                    binding.rvEvent.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    myEventAdapter.setData(state.data)
                    binding.rvEvent.adapter = myEventAdapter
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