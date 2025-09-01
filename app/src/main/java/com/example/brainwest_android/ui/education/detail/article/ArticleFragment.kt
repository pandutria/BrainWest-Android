package com.example.brainwest_android.ui.education.detail.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.databinding.FragmentArticleBinding
import com.example.brainwest_android.ui.education.EducationViewModel
import com.example.brainwest_android.ui.education.EducationViewModelFactory
import com.example.brainwest_android.utils.State

class ArticleFragment : Fragment() {
    lateinit var binding: FragmentArticleBinding

    private val viewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory(EducationRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_articleFragment_to_educationFragment)
        }

        showData()
        return binding.root
    }

    fun showData() {
        val id = arguments?.getInt("id", 0)
        viewModel.getEducationById(requireContext(), id!!)
        viewModel.getEducationByIdResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.wvArticle.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }

                is State.Success -> {
                    binding.wvArticle.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE

                    val link = state.data.link

                    if (!link.isNullOrEmpty()) {
                        binding.wvArticle.apply {
                            settings.javaScriptEnabled = true
                            webViewClient = object : android.webkit.WebViewClient() {
                                override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                                    binding.pbLoading.visibility = View.VISIBLE
                                    binding.wvArticle.visibility = View.GONE
                                }

                                override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                                    binding.pbLoading.visibility = View.GONE
                                    binding.wvArticle.visibility = View.VISIBLE
                                }
                            }
                            loadUrl(link)
                        }
                    }
                }

                is State.Error -> {
                    findNavController().navigate(R.id.action_articleFragment_to_educationFragment)
                }
            }
        }
    }
}