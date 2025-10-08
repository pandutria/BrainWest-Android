package com.example.brainwest_android.ui.scan.upload

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentUploadScanBinding
import android.companion.CompanionDeviceManager.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.repository.DetectionRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.utils.Helper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class UploadScanFragment : Fragment() {
    lateinit var binding: FragmentUploadScanBinding

    var imageUri: String? = null
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val viewModel: UploadScanViewModel by viewModels {
        UploadScanViewModelFactory(DetectionRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUploadScanBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK && result.data != null) {
                    val uri = result.data?.data
                    if (uri != null) {
                        requireContext().contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )

                        imageUri = uri.toString()
                        Glide.with(requireContext())
                            .load(imageUri)
                            .into(binding.imgImage)

                        binding.etPath.setText(imageUri)

                        binding.layoutUpload.visibility = View.GONE
                        binding.cardImage.visibility = View.VISIBLE
                    }
                }
            }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) openImagePicker()
                else Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT)
                    .show()
            }

        binding.layoutUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        binding.btnDelete.setOnClickListener {
            binding.layoutUpload.visibility = View.VISIBLE
            binding.cardImage.visibility = View.GONE
            binding.etPath.text.clear()
        }

        binding.btnDetection.setOnClickListener {
            if (binding.etPath.text == null || binding.etPath.text.toString().isEmpty())
                return@setOnClickListener Helper.showErrorToast(requireContext() ,"Choose image first!!")
            predict()
        }

        return binding.root
    }

    private fun predict() {
        val filePart = prepareFilePart(imageUri!!)
        viewModel.uploadScan(filePart)
        viewModel.predictionResult.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {
                    binding.btnDetection.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.btnDetection.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE

                    val bundle = Bundle().apply {
                        putString("prediction", state.data.label)
                        putDouble("confidence", state.data.confidence)
                    }
                    findNavController().navigate(R.id.action_uploadFragment_to_resultFragment, bundle)
                    imageUri = null
                    binding.etPath.text.clear()
                }
                is State.Error -> {
                    binding.btnDetection.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun prepareFilePart(uriString: String): MultipartBody.Part {
        val uri = Uri.parse(uriString)
        val inputStream = requireContext().contentResolver.openInputStream(uri)!!
        val file = File(requireContext().cacheDir, getFileName(uri))
        val outputStream = FileOutputStream(file)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }


    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        return result ?: "unknown_file.jpg"
    }


    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
    }
}