package com.example.brainwest_android.ui.consultation.chating

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.local.GeneralPref
import com.example.brainwest_android.data.repository.ChatConsultationRepository
import com.example.brainwest_android.data.repository.ConsultationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentChatingConsultationBinding
import com.example.brainwest_android.ui.adapter.ChatConsultationAdapter
import com.example.brainwest_android.utils.Helper

class ChatingConsultationFragment : Fragment() {
    lateinit var binding: FragmentChatingConsultationBinding

    private val viewModel: ChatingConsultationViewModel by viewModels {
        ChatingConsultationViewModelFactory(
            ConsultationRepository(),
            ChatConsultationRepository(requireContext())
        )
    }

    private lateinit var adapter: ChatConsultationAdapter
    var userId = 0
    var doctorId = 0
    lateinit var chatId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatingConsultationBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        showData()

        doctorId = requireArguments().getInt("doctor_id")
        userId = GeneralPref(requireContext()).getUserId()
        Log.d("userId", userId.toString())
        Log.d("doctorId", doctorId.toString())
        chatId = if (doctorId < userId) "${doctorId}_${userId}" else "${userId}_${doctorId}"

        adapter = ChatConsultationAdapter(mutableListOf(), userId)
        binding.rvChat.adapter = adapter

        viewModel.startListening(userId, doctorId)
        viewModel.messages.observe(viewLifecycleOwner) { newMessages ->
            adapter.setMessages(newMessages)
            binding.rvChat.scrollToPosition(newMessages.size - 1)
        }

        binding.btnSend.setOnClickListener {
            if (binding.etMessage.text.toString().isEmpty()) {
                binding.etMessage.error = "Message cannot be empty"
                Helper.showErrorToast(requireContext(), "All field must be filled")
                return@setOnClickListener
            }
            sendMessage()
        }

        viewModel.sendResult.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.btnSend.visibility = View.GONE
                    binding.pbLoadingChat.visibility = View.VISIBLE
                }
                is State.Success -> {
                    val messageText = binding.etMessage.text.toString()
                    viewModel.sendMessage(userId, doctorId, messageText)
                    binding.etMessage.text.clear()
                    binding.btnSend.visibility = View.VISIBLE
                    binding.pbLoadingChat.visibility = View.GONE
                }
                is State.Error -> {
                    Helper.showErrorLog(state.message)
                    Helper.showErrorToast(requireContext(), state.message)
                    binding.etMessage.text.clear()
                }
            }
        }

        return binding.root
    }

    fun sendMessage() {
        val id = requireArguments().getInt("doctor_id")
        viewModel.sendMessageToAPI(id, binding.etMessage.text.toString())
    }

    fun showData() {
        val id = requireArguments().getInt("doctor_id")
        viewModel.getDoctorById(id)
        viewModel.getByIdResult.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.scroll.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }

                is State.Success -> {
                    binding.tvName.text = "Dr. ${state.data.user.fullname}"
                    binding.tvNameHeader.text = "Dr. ${state.data.user.fullname}"
                    binding.tvSpesialist.text = "Spesialis ${state.data.specialization}"
                    binding.tvHospital.text = state.data.hospital
                    binding.tvRating.text = state.data.rating
                    binding.tvExperiences.text =
                        "154 Ulasan, Pengalaman ${state.data.experience} Tahun"

                    Glide.with(requireContext())
                        .load(state.data.image)
                        .into(binding.imgImage)

                    Glide.with(requireContext())
                        .load(state.data.image)
                        .into(binding.imgImageTwo)

                    binding.scroll.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }

                is State.Error -> {
                    findNavController().popBackStack()
                    Helper.showErrorLog(state.message)
                }
            }
        }
    }
}