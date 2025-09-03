package com.example.brainwest_android.ui.event.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.databinding.FragmentDetailEventBinding
import com.example.brainwest_android.ui.event.EventViewModel
import com.example.brainwest_android.ui.event.EventViewModelfactory
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State

class DetailEventFragment : Fragment() {
    lateinit var binding: FragmentDetailEventBinding

    private val viewModel: DetailEventViewModel by viewModels {
        DetailEventViewModelfactory(EventRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailEventBinding.inflate(layoutInflater)
        showData()
        return binding.root
    }

    fun showData() {
        val id = arguments?.getInt("id")
        viewModel.getEventById(requireContext(), id!!)
        viewModel.getEventBydIdResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.layoutContent.visibility = View.GONE
                    binding.layoutBtnBuy.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.layoutContent.visibility = View.VISIBLE
                    binding.layoutBtnBuy.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE

                    binding.tvTitle.text = state.data.title
                    binding.tvCity.text = state.data.city
                    binding.tvDate.text = state.data.date
                    binding.tvDesc.text = state.data.desc
                    binding.tvTime.text = state.data.time
                    binding.tvAddress.text = state.data.address
                    binding.tvPrice.text = state.data.price

                    Glide.with(requireContext())
                        .load(state.data.image)
                        .into(binding.imgImage)
                }
                is State.Error -> {
                    findNavController().popBackStack()
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }
    }
}