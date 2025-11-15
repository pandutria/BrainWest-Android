package com.example.brainwest_android.ui.scan.result

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentResultScanBinding
import com.example.brainwest_android.ui.adapter.ConditionAdapter
import com.example.brainwest_android.ui.adapter.RecomendationAdapter
import com.example.brainwest_android.ui.parent.ConsultationActivity
import com.example.brainwest_android.ui.parent.MainActivity

class ResultScanFragment : Fragment() {
    private lateinit var binding: FragmentResultScanBinding
    private lateinit var conditionAdapter: ConditionAdapter
    private lateinit var recomendationAdapter: RecomendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultScanBinding.inflate(inflater, container, false)

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
        val prediction = arguments?.getString("prediction") ?: "unknown"
        val rawConfidence = arguments?.getDouble("confidence") ?: 0.0

        val percent = if (prediction.lowercase() == "notumor" || prediction.lowercase() == "no_tumor") {
            100
        } else {
            (rawConfidence * 100).toInt().coerceIn(0, 100)
        }

        val riskLevel: String

        when {
            percent <= 40 -> {
                riskLevel = "Tinggi"
            }
            percent <= 70 -> {
                riskLevel = "Sedang"
            }
            percent <= 90 -> {
                riskLevel = "Rendah"
            }
            else -> {
                riskLevel = "Sangat Rendah"
            }
        }

        binding.progressAccuration.max = 100
        binding.progressAccuration.progress = percent
        binding.tvAccuration.text = "$percent%"
        binding.tvRisk.text = riskLevel

        binding.tvDiagnose.text = prediction

        val predictionArray = arrayListOf(
            getString(R.string.explanation_glioma),
            getString(R.string.explanation_meningioma),
            getString(R.string.explanation_no_tumor),
            getString(R.string.explanation_pituitary)
        )

        val index = when (prediction.lowercase()) {
            "glioma" -> 0
            "meningioma" -> 1
            "notumor", "no_tumor" -> 2
            "pituitary" -> 3
            else -> -1
        }

        binding.tvExlpanation.text = if (index in predictionArray.indices) {
            predictionArray[index]
        } else {
            getString(R.string.default_explanation)
        }

        showCondition(prediction)
        showRecomendation(prediction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
    }

    private fun showCondition(prediction: String) {
        conditionAdapter = ConditionAdapter()
        binding.rvCondition.adapter = conditionAdapter


        val conditionsList = when (prediction.lowercase()) {
            "glioma" -> arrayListOf(
                "Glioma adalah tumor otak yang berasal dari sel glial yang mendukung neuron dan dapat bersifat jinak atau ganas.",
                "Segera konsultasikan hasil ini dengan dokter spesialis bedah saraf atau onkologi untuk pemeriksaan lanjutan.",
                "Penanganan dapat meliputi pembedahan, radioterapi, atau kemoterapi tergantung pada tingkat keparahan dan lokasi tumor."
            )
            "meningioma" -> arrayListOf(
                "Meningioma merupakan tumor yang tumbuh pada selaput pelindung otak dan biasanya bersifat jinak.",
                "Meskipun jinak, tumor ini dapat menekan jaringan otak sehingga perlu pemeriksaan lanjutan oleh dokter spesialis saraf.",
                "Pembedahan atau pemantauan rutin mungkin diperlukan tergantung pada ukuran dan gejala yang dialami."
            )
            "notumor", "no_tumor" -> arrayListOf(
                "Hasil menunjukkan tidak ada tanda-tanda tumor otak pada citra MRI yang dianalisis.",
                "Kondisi otak tampak normal, namun tetap disarankan untuk menjaga kesehatan dan melakukan pemeriksaan rutin.",
                "Jika timbul gejala baru seperti sakit kepala berat, gangguan penglihatan, atau kejang, segera konsultasikan ke dokter."
            )
            "pituitary" -> arrayListOf(
                "Tumor pituitari tumbuh pada kelenjar pituitari yang mengatur hormon tubuh dan dapat menyebabkan gangguan hormonal.",
                "Gejalanya bisa berupa gangguan penglihatan, perubahan berat badan, kelelahan, atau gangguan siklus menstruasi.",
                "Konsultasikan hasil ini dengan dokter spesialis endokrinologi atau bedah saraf untuk pemeriksaan lanjutan."
            )
            else -> arrayListOf(
                "Hasil prediksi tidak dapat diidentifikasi secara pasti.",
                "Disarankan melakukan pemeriksaan MRI lanjutan atau konsultasi dengan tenaga medis profesional.",
                "Jangan hanya mengandalkan hasil ini untuk keputusan medis tanpa konfirmasi dokter."
            )
        }

        conditionAdapter.setData(conditionsList)
    }

    private fun showRecomendation(prediction: String) {
        recomendationAdapter = RecomendationAdapter()
        binding.rvRecomendation.adapter = recomendationAdapter

        val recomendationList = when (prediction.lowercase()) {
            "glioma" -> arrayListOf(
                "Segera periksakan diri ke dokter spesialis bedah saraf atau onkologi untuk evaluasi lebih lanjut.",
                "Jaga pola makan sehat, cukup tidur, dan hindari stres berlebihan untuk mendukung pemulihan.",
                "Ikuti semua prosedur medis yang disarankan seperti operasi, radioterapi, atau kemoterapi sesuai kondisi.",
                "Libatkan keluarga dan dukungan emosional dalam proses pengobatan untuk menjaga kesehatan mental."
            )
            "meningioma" -> arrayListOf(
                "Konsultasikan hasil ini dengan dokter spesialis saraf untuk menentukan langkah penanganan yang tepat.",
                "Jika tidak menimbulkan gejala berat, dokter mungkin menyarankan observasi rutin melalui MRI berkala.",
                "Hindari aktivitas berat jika mengalami pusing atau gangguan penglihatan.",
                "Pertahankan pola hidup sehat untuk mendukung fungsi otak secara optimal."
            )
            "notumor", "no_tumor" -> arrayListOf(
                "Tidak ditemukan tumor otak. Pertahankan gaya hidup sehat dan lakukan pemeriksaan rutin sesuai anjuran.",
                "Konsumsi makanan bergizi seimbang dan cukup istirahat untuk menjaga kesehatan otak.",
                "Hindari stres berlebihan dan pastikan pola tidur teratur.",
                "Jika mengalami gejala baru yang mencurigakan, segera lakukan pemeriksaan medis."
            )
            "pituitary" -> arrayListOf(
                "Konsultasikan hasil ini dengan dokter spesialis endokrinologi atau bedah saraf.",
                "Pemantauan kadar hormon mungkin diperlukan untuk menilai dampak tumor pada fungsi tubuh.",
                "Jaga keseimbangan gaya hidup dengan pola makan bergizi dan aktivitas ringan.",
                "Catat perubahan gejala seperti penglihatan kabur atau perubahan berat badan untuk dilaporkan ke dokter."
            )
            else -> arrayListOf(
                "Hasil prediksi tidak jelas. Sebaiknya lakukan pemeriksaan MRI lanjutan atau konsultasi dengan dokter spesialis saraf.",
                "Jangan mengandalkan hasil otomatis ini untuk pengambilan keputusan medis.",
                "Pantau gejala yang dirasakan dan segera cari pertolongan medis jika memburuk.",
                "Dokumentasikan gejala dan waktu kemunculannya untuk membantu dokter dalam diagnosis."
            )
        }

        recomendationAdapter.setData(recomendationList)
    }
}
