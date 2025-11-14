package com.example.brainwest_android.ui.diagnose.result

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentQuestionDiagnoseBinding
import com.example.brainwest_android.databinding.FragmentResultDiagnoseBinding
import com.example.brainwest_android.ui.adapter.ConditionAdapter
import com.example.brainwest_android.ui.adapter.RecomendationAdapter
import com.example.brainwest_android.ui.parent.ConsultationActivity
import com.example.brainwest_android.ui.parent.MainActivity

class ResultDiagnoseFragment : Fragment() {
    lateinit var binding: FragmentResultDiagnoseBinding
    private lateinit var conditionAdapter: ConditionAdapter
    private lateinit var recomendationAdapter: RecomendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultDiagnoseBinding.inflate(layoutInflater)

        setupButtons()
        showScanResult()

        return binding.root
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack(R.id.uploadFragment, false)
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
            requireActivity().finish()
        }
        binding.btnConsul.setOnClickListener {
            val intent = Intent(requireContext(), ConsultationActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
            requireActivity().finish()
        }
    }

    private fun showScanResult() {
        val totalScore = arguments?.getDouble("score") ?: 0.0
        val percent = totalScore.toInt().coerceIn(0, 100)

        val category = when {
            percent <= 20 -> "Buruk"
            percent <= 50 -> "Cukup"
            percent <= 80 -> "Baik"
            else -> "Sangat Baik"
        }

        binding.progressAccuration.max = 100
        binding.progressAccuration.progress = percent

        binding.tvAccuration.text = "$percent%"

        binding.tvDiagnose.text = "$percent% ($category)"

        val explanation = when {
            percent <= 20 -> "Kondisi kesehatan Anda tergolong kurang baik. Disarankan segera memperbaiki pola hidup dan konsultasi dengan tenaga medis."
            percent <= 50 -> "Kesehatan Anda cukup stabil, tetapi masih ada beberapa hal yang bisa ditingkatkan seperti pola tidur dan asupan gizi."
            percent <= 80 -> "Kesehatan Anda baik! Pertahankan kebiasaan sehat yang sudah dilakukan dan terus rutin beraktivitas fisik."
            else -> "Kesehatan Anda sangat baik! Terus pertahankan gaya hidup sehat dan jadikan kebiasaan baik ini sebagai contoh bagi orang lain."
        }

        val risk = when {
            percent <= 20 -> "Tinggi"
            percent <= 50 -> "Sedang"
            percent <= 80 -> "Rendah"
            else -> "Sangat Rendah"
        }

        binding.tvExlpanation.text = explanation
        binding.tvRisk.text = risk

        showCondition(totalScore)
        showRecomendation(totalScore)
    }

    private fun showCondition(score: Double) {
        conditionAdapter = ConditionAdapter()
        binding.rvCondition.adapter = conditionAdapter

        val conditionsList = when {
            score <= 20 -> arrayListOf(
                "Kondisi kesehatan saat ini tergolong buruk.",
                "Perlu perhatian serius terhadap pola hidup, nutrisi, dan aktivitas harian.",
                "Segera pertimbangkan konsultasi dengan tenaga kesehatan profesional."
            )
            score <= 50 -> arrayListOf(
                "Kondisi kesehatan cukup, namun ada beberapa area yang perlu diperbaiki.",
                "Perhatikan pola makan, tidur, dan aktivitas fisik.",
                "Lakukan pemeriksaan rutin untuk memastikan kesehatan tetap stabil."
            )
            score <= 80 -> arrayListOf(
                "Kondisi kesehatan baik.",
                "Lanjutkan pola hidup sehat dan rutin memonitor kesehatan.",
                "Tetap aktif secara fisik dan jaga nutrisi."
            )
            else -> arrayListOf(
                "Kondisi kesehatan sangat baik.",
                "Pertahankan kebiasaan sehat yang sudah dilakukan.",
                "Anda bisa menjadi contoh bagi orang lain dalam menjaga kesehatan."
            )
        }

        conditionAdapter.setData(conditionsList)
    }

    private fun showRecomendation(score: Double) {
        recomendationAdapter = RecomendationAdapter()
        binding.rvRecomendation.adapter = recomendationAdapter

        val recomendationList = when {
            score <= 20 -> arrayListOf(
                "Segera evaluasi pola makan dan aktivitas harian.",
                "Tingkatkan konsumsi makanan bergizi dan perbanyak sayur serta buah.",
                "Tidur cukup setiap hari dan hindari stres berlebihan.",
                "Pertimbangkan konsultasi dengan dokter atau ahli gizi untuk penanganan lebih lanjut."
            )
            score <= 50 -> arrayListOf(
                "Perbaiki pola makan dan olahraga secara teratur.",
                "Cek rutin kesehatan dasar seperti tekanan darah dan gula darah.",
                "Kurangi kebiasaan yang dapat menurunkan kesehatan seperti begadang atau konsumsi junk food.",
                "Pertahankan kebiasaan baik dan fokus pada area yang masih perlu perbaikan."
            )
            score <= 80 -> arrayListOf(
                "Lanjutkan pola hidup sehat yang sudah diterapkan.",
                "Tambahkan variasi olahraga ringan dan aktivitas yang menstimulasi otak.",
                "Pantau kesehatan secara berkala.",
                "Tetap konsumsi makanan bergizi seimbang dan cukup istirahat."
            )
            else -> arrayListOf(
                "Pertahankan gaya hidup sehat saat ini.",
                "Bagikan tips dan kebiasaan sehat dengan keluarga atau teman.",
                "Tetap aktif secara fisik dan mental.",
                "Lakukan pemeriksaan kesehatan rutin untuk memastikan semuanya tetap optimal."
            )
        }

        recomendationAdapter.setData(recomendationList)
    }

}