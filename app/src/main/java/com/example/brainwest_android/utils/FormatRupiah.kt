package com.example.brainwest_android.utils

import java.text.NumberFormat
import java.util.Locale

object FormatRupiah {
    fun format(number: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
        return "Rp ${formatter.format(number)}"
    }
}