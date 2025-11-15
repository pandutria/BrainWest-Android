package com.example.brainwest_android.ui.rehabilitation.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.RehabilitationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentInputRehabilitationBinding
import com.example.brainwest_android.utils.Helper

class InputRehabilitationFragment : Fragment() {
    lateinit var binding: FragmentInputRehabilitationBinding

    private val viewModel: InputRehabilitationViewModel by viewModels {
        InputRehabilitationViewModelFactory(RehabilitationRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputRehabilitationBinding.inflate(inflater, container, false)
        setupSpinner()

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        binding.btnTicket.setOnClickListener {
            findNavController().navigate(R.id.action_inputFragment_to_saveFragment)
        }

        binding.btnStart.setOnClickListener {
            if (binding.etAge.text.toString().isEmpty() ||
                (!binding.rbMale.isChecked && !binding.rbFemale.isChecked) ||
                binding.spinnerHint.text.toString() == "Pilih status..." ||
                (!binding.rb1.isChecked && !binding.rb2.isChecked && !binding.rb3.isChecked && !binding.rb4.isChecked)) {
                Helper.showErrorToast(requireContext(), "Semua data harus diisi")
                return@setOnClickListener
            }
            sendData()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
    }

    fun sendData() {
        val age = binding.etAge.text.toString()
        val gender = if (binding.rbMale.isChecked) "laki-laki" else "perempuan"
        val medical_status = binding.spinnerHint.text.toString()
        val time_of_diagnosis = if (binding.rb1.isChecked) "< 1 bulan"
                                else if (binding.rb2.isChecked) "1–2 bulan"
                                else if (binding.rb3.isChecked) "3 bulan"
                                else "3 bulan lebih"

        viewModel.getVideoByRehab(age, gender, medical_status, time_of_diagnosis)
        viewModel.result.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.GONE
                }
                is State.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.btnStart.visibility = View.VISIBLE
                    val bundle = Bundle().apply {
                        putParcelableArrayList("videos", ArrayList(state.data))
                    }
                    findNavController().navigate(R.id.action_inputFragment_to_listFragment, bundle)
                }
                is State.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.btnStart.visibility = View.VISIBLE
                    Helper.showErrorToast(requireContext(), state.message)
                    Helper.showErrorLog(state.message)
                }
            }
        }
    }

    fun setupSpinner() {
        val statusList = listOf("Stroke ringan", "Stroke sedang", "Stroke berat")
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            statusList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = TextView(context)
                v.text = ""
                v.height = 0
                return v
            }
        }
        binding.spinnerMedice.adapter = adapter
        binding.spinnerMedice.setSelection(-1, false)

        binding.spinnerMedice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.spinnerHint.text = statusList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.spinnerHint.text = "Pilih status..."
            }
        }
    }
}