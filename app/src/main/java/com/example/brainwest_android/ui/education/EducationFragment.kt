package com.example.brainwest_android.ui.education

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.databinding.FragmentEducationBinding
import com.example.brainwest_android.ui.adapter.ArticleAdapter
import com.example.brainwest_android.ui.adapter.SliderArticleAdapter
import com.example.brainwest_android.ui.adapter.VideoAdapter
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.data.state.State

class EducationFragment : Fragment() {
    lateinit var binding: FragmentEducationBinding
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    private val viewModel: EducationViewModel by viewModels {
        EducationViewModelFactory(EducationRepository())
    }

    lateinit var articleAdapter: ArticleAdapter
    lateinit var videoAdapter: VideoAdapter

    private var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEducationBinding.inflate(layoutInflater)
        setupImageSlider()

        articleAdapter = ArticleAdapter { edu ->
            val bundle = Bundle().apply {
                putInt("id", edu.id!!)
            }
            findNavController().navigate(R.id.action_educationFragment_to_articleFragment, bundle)

        }

        videoAdapter = VideoAdapter { edu ->
            val bundle = Bundle().apply {
                putInt("id", edu.id!!)
            }
            findNavController().navigate(R.id.action_educationFragment_to_videoFragment, bundle)
        }

        showDataArticle()
        showDataVideo()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    fun showDataVideo() {
        if (articleAdapter.itemCount > 0) return
        viewModel.getAllArticle(requireContext())
        viewModel.getAllEducationResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {}
                is State.Success -> {
                    videoAdapter.setData(state.data.filter { x -> x.category == "video" })
                    binding.rvVideo.adapter = videoAdapter
                    binding.rvVideo.layoutManager?.onRestoreInstanceState(viewModel.videoRvState)
                }
                is State.Error -> {
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }
    }

    fun showDataArticle() {
        if (articleAdapter.itemCount > 0) return
        viewModel.getAllArticle(requireContext())
        viewModel.getAllEducationResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {}
                is State.Success -> {
                    articleAdapter.setData(state.data.filter { x -> x.category == "article" })
                    binding.rvArticle.adapter = articleAdapter
                    binding.rvArticle.layoutManager?.onRestoreInstanceState(viewModel.articleRvState)
                }
                is State.Error -> {
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }
    }

    fun setupImageSlider() {
        val images =
            listOf(R.drawable.example_article1, R.drawable.example_article1, R.drawable.example_article1)
        val icons = listOf(
            R.drawable.image_slider_icon1,
            R.drawable.image_slider_icon2,
            R.drawable.image_slider_icon3
        )
        val texts = listOf(
            "Ragam Perilaku Gejala ”Brain Rot” dan Cara Menjaga Kesehatan Otak",
            "Ragam Perilaku Gejala ”Brain Rot” dan Cara Menjaga Kesehatan Otak",
            "Ragam Perilaku Gejala ”Brain Rot” dan Cara Menjaga Kesehatan Otak"
        ) 

        val adapter = SliderArticleAdapter(images, icons, texts)
        binding.viewPager.adapter = adapter

        val runnable = object : Runnable {
            override fun run() {
                if (currentPage == images.size) currentPage = 0
                binding.viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 4000)
            }
        }
        handler.postDelayed(runnable, 4000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        viewModel.scrollY.value = binding.nestedScroll.scrollY
        viewModel.articleRvState = binding.rvArticle.layoutManager?.onSaveInstanceState()
        viewModel.videoRvState = binding.rvVideo.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        binding.nestedScroll.post {
            binding.nestedScroll.scrollTo(0, viewModel.scrollY.value ?: 0)
        }
        binding.rvArticle.layoutManager?.onRestoreInstanceState(viewModel.articleRvState)
        binding.rvVideo.layoutManager?.onRestoreInstanceState(viewModel.videoRvState)
    }
}