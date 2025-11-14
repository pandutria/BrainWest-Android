package com.example.brainwest_android.ui.community.chating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.GeneralPref
import com.example.brainwest_android.data.repository.ChatCommunityRepository
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentChatingCommunityBinding
import com.example.brainwest_android.databinding.FragmentChatingConsultationBinding
import com.example.brainwest_android.ui.adapter.ChatCommunityAdapter
import com.example.brainwest_android.utils.Helper

class ChatingCommunityFragment : Fragment() {
    lateinit var binding: FragmentChatingCommunityBinding

    private val viewModel: ChatingCommunityViewModel by viewModels {
        ChatingCommunityViewModelFactory(CommunityRepository(), ChatCommunityRepository(requireContext()))
    }

    lateinit var adapter: ChatCommunityAdapter
    var groupId = 0
    var userId = 0

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

        groupId = requireActivity().intent.getIntExtra("id", 0)
        userId = GeneralPref(requireContext()).getUserId()

        adapter = ChatCommunityAdapter(mutableListOf(), userId)
        binding.rvChat.adapter = adapter

        viewModel.startListening(groupId)
        viewModel.messages.observe(viewLifecycleOwner) { newMessages ->
            adapter.setMessages(newMessages)
            binding.rvChat.scrollToPosition(newMessages.size - 1)
        }

        binding.btnSend.setOnClickListener {
            val messageText = binding.etMessage.text.toString()
            if (messageText.isEmpty()) {
                Helper.showErrorToast(requireContext(), "Message cannot be empty")
                return@setOnClickListener
            }
            viewModel.sendMessageToApi(groupId, binding.etMessage.text.toString())
        }

        viewModel.post.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoadingChat.visibility = View.VISIBLE
                    binding.btnSend.visibility = View.GONE
                }
                is State.Success -> {
                    binding.pbLoadingChat.visibility = View.GONE
                    binding.btnSend.visibility = View.VISIBLE
                    viewModel.sendMessage(groupId, binding.etMessage.text.toString())
                    binding.etMessage.text.clear()
                }
                is State.Error -> {
                    binding.pbLoadingChat.visibility = View.GONE
                    binding.btnSend.visibility = View.VISIBLE
                    binding.etMessage.text.clear()
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }

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