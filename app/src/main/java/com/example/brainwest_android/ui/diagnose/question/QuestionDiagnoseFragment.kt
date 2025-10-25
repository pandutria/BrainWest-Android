package com.example.brainwest_android.ui.diagnose.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.DiagnoseRepository
import com.example.brainwest_android.databinding.FragmentQuestionDiagnoseBinding
import com.example.brainwest_android.utils.Helper


class QuestionDiagnoseFragment : Fragment() {
    lateinit var binding: FragmentQuestionDiagnoseBinding

    var currNumber = 1
    val q1 = "1. Seberapa sering Anda lupa nama orang yang baru dikenalkan?"
    val q2 = "2. Apakah Anda merasa kesulitan mengingat hal-hal kecil (misalnya lokasi benda)?"
    val q3 = "3. Seberapa cepat Anda bisa memahami informasi baru?"
    val q4 = "4. Apakah Anda merasa mudah terdistraksi?"
    val q5 = "5. Apakah Anda bisa fokus dalam satu aktivitas lebih dari 30 menit?"
    val q6 = "6. Apakah Anda merasa cemas atau gelisah akhir-akhir ini?"
    val q7 = "7. Seberapa sering Anda mengalami suasana hati yang berubah drastis?"
    val q8 = "8. Apakah Anda merasa kewalahan dengan tugas sehari-hari?"
    val q9 = "9. Apakah Anda bisa tidur nyenyak tanpa terbangun di malam hari?"
    val q10 = "10. Apakah Anda bangun dengan rasa segar atau justru lelah?"
    val q11 = "11. Seberapa sering Anda berolahraga dalam seminggu?"
    val q12 = "12. Seberapa banyak konsumsi makanan bergizi (sayur, buah, ikan)?"
    val q13 = "13. Berapa jam screen time Anda dalam sehari?"
    val q14 = "14. Apakah Anda merokok atau mengonsumsi alkohol?"
    val q15 = "15. Apakah Anda rutin melakukan stimulasi otak (baca buku, puzzle, game otak)?"

    var answer1 = ""
    var answer2 = ""
    var answer3 = ""
    var answer4 = ""
    var answer5 = ""
    var answer6 = ""
    var answer7 = ""
    var answer8 = ""
    var answer9 = ""
    var answer10 = ""
    var answer11 = ""
    var answer12 = ""
    var answer13 = ""
    var answer14 = ""
    var answer15 = ""

    var sentCount = 0
    var scores = mutableListOf<Double>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestionDiagnoseBinding.inflate(layoutInflater)

        binding.btnPrev.visibility = View.GONE
        binding.btnNext.setOnClickListener {
            if (currNumber == 1) {
                answer1 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 2) {
                answer2 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 3) {
                answer3 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 4) {
                answer4 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 5) {
                answer5 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 6) {
                answer6 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 7) {
                answer7 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 8) {
                answer8 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 9) {
                answer9 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 10) {
                answer10 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 11) {
                answer11 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 12) {
                answer12 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 13) {
                answer13 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 14) {
                answer14 = binding.etAnswer.text.toString()
                binding.etAnswer.text.clear()
                currNumber += 1
                loadQuestion()
                return@setOnClickListener
            }
            if (currNumber == 15) {
                answer15 = binding.etAnswer.text.toString()

                val questions = listOf(q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12,q13,q14,q15)
                val answers = listOf(answer1,answer2,answer3,answer4,answer5,
                    answer6,answer7,answer8,answer9,answer10,
                    answer11,answer12,answer13,answer14,answer15)

                sentCount = 0
                scores.clear()

                for (i in 0 until 15) {
                    sendAnswerToApi(questions[i], answers[i]) {
                        val bundle = Bundle().apply {
                            val totalScore = scores.sum()
                            putDouble("score", totalScore)
                        }
                        findNavController().navigate(R.id.action_questionFragment_to_resultFragment, bundle)
                    }
                }
                return@setOnClickListener
            }
        }

        binding.btnPrev.setOnClickListener {
            if (currNumber == 2) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer1)
                return@setOnClickListener
            }
            if (currNumber == 3) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer2)
                return@setOnClickListener
            }
            if (currNumber == 4) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer3)
                return@setOnClickListener
            }
            if (currNumber == 5) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer4)
                return@setOnClickListener
            }
            if (currNumber == 6) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer5)
                return@setOnClickListener
            }
            if (currNumber == 7) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer6)
                return@setOnClickListener
            }
            if (currNumber == 8) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer7)
                return@setOnClickListener
            }
            if (currNumber == 9) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer8)
                return@setOnClickListener
            }
            if (currNumber == 10) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer9)
                return@setOnClickListener
            }
            if (currNumber == 11) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer10)
                return@setOnClickListener
            }
            if (currNumber == 12) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer11)
                return@setOnClickListener
            }
            if (currNumber == 13) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer12)
                return@setOnClickListener
            }
            if (currNumber == 14) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer13)
                return@setOnClickListener
            }
            if (currNumber == 15) {
                currNumber -= 1
                loadQuestion()
                binding.etAnswer.setText(answer14)
                return@setOnClickListener
            }
        }
        return binding.root
    }

    fun sendAnswerToApi(question: String, answer: String, onComplete: () -> Unit) {
        val viewModel = QuestionDiagnoseViewModel(DiagnoseRepository())
        viewModel.result.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                val score = response.body()?.score ?: 0.0
                scores.add(score)
                sentCount += 1

                if (sentCount == 15) {
                    val totalScore = scores.sum()
//                    Helper.showSuccessToast(requireContext(), "Hasil total: $totalScore")
                    onComplete()
                }
            } else {
                Helper.showErrorToast(requireContext(), "Gagal kirim pertanyaan: $question")
            }
        }

        viewModel.diagnose(question, answer)
    }



    fun loadQuestion() {
        if (currNumber == 1) {
            binding.tvQuestion.text = q1
            binding.btnPrev.visibility = View.GONE
        }
        if (currNumber == 2) {
            binding.tvQuestion.text = q2
            binding.btnPrev.visibility = View.VISIBLE
        }
        if (currNumber == 3) {
            binding.tvQuestion.text = q3
        }
        if (currNumber == 4) {
            binding.tvQuestion.text = q4
        }
        if (currNumber == 5) {
            binding.tvQuestion.text = q5
        }
        if (currNumber == 6) {
            binding.tvQuestion.text = q6
        }
        if (currNumber == 7) {
            binding.tvQuestion.text = q7
        }
        if (currNumber == 8) {
            binding.tvQuestion.text = q8
        }
        if (currNumber == 9) {
            binding.tvQuestion.text = q9
        }
        if (currNumber == 10) {
            binding.tvQuestion.text = q10
        }
        if (currNumber == 11) {
            binding.tvQuestion.text = q11
        }
        if (currNumber == 12) {
            binding.tvQuestion.text = q12
        }
        if (currNumber == 13) {
            binding.tvQuestion.text = q13
        }
        if (currNumber == 14) {
            binding.tvQuestion.text = q14
            binding.btnNext.text = "Berikutnya"
        }
        if (currNumber == 15) {
            binding.tvQuestion.text = q15
            binding.btnNext.text = "Selesai"
        }

    }
}