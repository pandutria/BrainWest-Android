package com.example.brainwest_android.ui.education.detail.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.databinding.FragmentVideoBinding
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.data.state.State
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class VideoFragment : Fragment() {
    lateinit var binding: FragmentVideoBinding
    private var player: ExoPlayer? = null

    private val viewModel: VideoViewModel by viewModels {
        VideoViewModelFactory(EducationRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.layoutContent.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData()
    }

    fun showData() {
        val id = arguments?.getInt("id", 0)
        viewModel.getEducationById(requireContext(), id!!)
        viewModel.getEducationByIdResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.layoutContent.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
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

                    binding.layoutContent.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }

                is State.Error -> {
                    Helper.showErrorToast(requireContext(), state.message)
                    findNavController().popBackStack()
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