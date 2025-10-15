package com.example.brainwest_android.ui.rehabilitation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.RehabilitationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentDetailRehabilitationBinding
import com.example.brainwest_android.utils.Helper
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class DetailRehabilitationFragment : Fragment() {
    lateinit var binding: FragmentDetailRehabilitationBinding
    private var player: ExoPlayer? = null

    private val viewModel: DetailRehabilitationViewModel by viewModels {
        DetailRehabilitationViewModelFactory(RehabilitationRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailRehabilitationBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        showData()

        return binding.root
    }
    fun showData() {
        val id = requireArguments().getInt("id")
        viewModel.getVideoRehabById(id)
        viewModel.result.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.layoutContent.visibility = View.GONE
                }
                is State.Success -> {
                    binding.tvTitle.text = state.data.title
                    binding.tvDesc.text = state.data.text

                    player = ExoPlayer.Builder(requireContext()).build()
                    binding.pvVideo.player = player

                    val mediaItem = MediaItem.fromUri(state.data.link!!)
                    player?.setMediaItem(mediaItem)
                    player?.prepare()
                    player?.play()

                    binding.pbLoading.visibility = View.GONE
                    binding.layoutContent.visibility = View.VISIBLE
                }
                is State.Error -> {
                    findNavController().popBackStack()
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}