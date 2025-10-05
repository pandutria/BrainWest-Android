package com.example.brainwest_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.ui.parent.ChatBotActivity
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentHomeBinding
import com.example.brainwest_android.ui.parent.DonationActivity
import com.example.brainwest_android.ui.parent.EventActivity
import com.example.brainwest_android.ui.adapter.SliderAdapter
import com.example.brainwest_android.ui.parent.ConsultationActivity

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelfactory()
    }

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
            val intent = Intent(requireContext(), EventActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
        binding.btnChatbot.setOnClickListener {
            val intent = Intent(requireContext(), ChatBotActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
        binding.btnDonation.setOnClickListener {
            val intent = Intent(requireContext(), DonationActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
        binding.layoutConsultation.setOnClickListener {
            val intent = Intent(requireContext(), ConsultationActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
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

    override fun onPause() {
        super.onPause()
//        viewModel.scrollY.value = binding.nestedScroll.scrollY
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        viewModel.scrollY.value = binding.nestedScroll.scrollY
    }

    override fun onResume() {
        super.onResume()
//        binding.nestedScroll.viewTreeObserver.addOnGlobalLayoutListener {
//            val savedScroll = viewModel.scrollY.value ?: 0
//            binding.nestedScroll.scrollTo(0, savedScroll)
//        }
    }
}