package com.example.brainwest_android.ui.event.transaction

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentTransactionEventBinding
import com.example.brainwest_android.utils.Helper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransactionEventFragment : Fragment() {
    lateinit var binding: FragmentTransactionEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSnap()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun showSnap() {
        binding.wvSnap.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE

        val snap_token = arguments?.getString("snap_token") ?: null

        if (snap_token == null) findNavController().popBackStack()

        val snapUrl = "https://app.sandbox.midtrans.com/snap/v2/vtweb/$snap_token"

        binding.wvSnap.settings.javaScriptEnabled = true
        binding.wvSnap.settings.domStorageEnabled = true
        binding.wvSnap.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.wvSnap.visibility = View.GONE
                binding.pbLoading.visibility = View.VISIBLE
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.pbLoading.visibility = View.GONE
                binding.wvSnap.visibility = View.VISIBLE

                if (url != null) {
                    when {
                        url.contains("finish") || url.contains("successful") -> {
                            viewLifecycleOwner.lifecycleScope.launch {
                                delay(3000)
                                Helper.showSuccessToast(requireContext(), "Tiket berhasil di beli")
                                findNavController().navigate(R.id.action_eventTransactionFragment_to_myEventTransactionFragment)
                            }
                        }
                        url.contains("pending") -> {
                            findNavController().popBackStack()
                        }
                        url.contains("error") || url.contains("cancel") -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            }

//            @Deprecated("Deprecated in Java")
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                if (url != null) {
//                    when {
//                        url.contains("finish") || url.contains("successful") -> {
//                            findNavController().popBackStack()
//                            return true
//                        }
//                        url.contains("pending") -> {
//                            findNavController().popBackStack()
//                            return true
//                        }
//                        url.contains("error") || url.contains("cancel") -> {
//                            findNavController().popBackStack()
//                            return true
//                        }
//                    }
//                }
//                return false
//            }
        }
        binding.wvSnap.loadUrl(snapUrl)

        binding.wvSnap.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.GONE
    }
}