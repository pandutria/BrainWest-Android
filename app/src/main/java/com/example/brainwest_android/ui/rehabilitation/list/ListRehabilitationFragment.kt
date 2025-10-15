package com.example.brainwest_android.ui.rehabilitation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.databinding.FragmentListRehabilitationBinding
import com.example.brainwest_android.ui.adapter.RehabilitationAdapter

class ListRehabilitationFragment : Fragment() {
    lateinit var binding: FragmentListRehabilitationBinding

    lateinit var adapter: RehabilitationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListRehabilitationBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        adapter = RehabilitationAdapter {rehab ->
            val bundle = Bundle().apply {
                putInt("id", rehab.id)
            }
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }

        val data = requireArguments().getParcelableArrayList<RehabilitationVideo>("videos")
        adapter.setData(data!!)
        binding.rvRehabilitation.adapter = adapter

        return binding.root
    }
}