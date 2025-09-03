package com.example.brainwest_android.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentHomeBinding
import com.example.brainwest_android.ui.adapter.SliderAdapter

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setupImageSlider()
        navigate()

        return binding.root
    }

    fun navigate() {
        binding.btnEvent.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_eventFragment)
        }
    }

    fun setupImageSlider() {
        val images = listOf(R.drawable.image_slider1, R.drawable.image_slider1, R.drawable.image_slider1)
        val icons = listOf(R.drawable.image_slider_icon1, R.drawable.image_slider_icon2, R.drawable.image_slider_icon3)

        val adapter = SliderAdapter(images, icons)
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