package com.example.brainwest_android.ui.education

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.brainwest_android.R
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.databinding.FragmentEducationBinding
import com.example.brainwest_android.ui.adapter.EducationAdapter
import com.example.brainwest_android.ui.adapter.SliderAdapter
import com.example.brainwest_android.ui.adapter.SliderArticleAdapter
import com.example.brainwest_android.utils.State

class EducationFragment : Fragment() {
    lateinit var binding: FragmentEducationBinding
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    private val viewModel: EducationViewModel by viewModels {
        EducationViewModelFactory(EducationRepository(), requireContext())
    }

    lateinit var educationAdapter: EducationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEducationBinding.inflate(layoutInflater)
        setupImageSlider()

        educationAdapter = EducationAdapter {edu ->

        }

        showDataArticle()

        return binding.root
    }

    fun showDataArticle() {
        viewModel.getAllArticle()
        viewModel.getAllEducationResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {}
                is State.Success -> {
                    educationAdapter.setData(state.data)
                    binding.rvArticle.adapter = educationAdapter
                }
                is State.Error -> {}
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
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}