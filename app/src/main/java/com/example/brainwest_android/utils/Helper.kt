package com.example.brainwest_android.utils

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.example.brainwest_android.R
import io.github.muddz.styleabletoast.StyleableToast

object Helper {
    fun showErrorLog(message: String) {
        Log.d("AppError", "Error : $message")
    }

    fun showSuccessToast(context: Context, message: String) {
        StyleableToast.Builder(context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(ContextCompat.getColor(context, R.color.successToast))
            .solidBackground()
            .cornerRadius(5)
            .textBold()
            .iconStart(R.drawable.ic_success)
            .textSize(14f)
            .length(3000)
            .gravity(Gravity.TOP)
            .font(R.font.poppins_medium)
            .show()
    }

    fun showWarningToast(context: Context, message: String) {
        StyleableToast.Builder(context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(ContextCompat.getColor(context, R.color.warningToast))
            .solidBackground()
            .cornerRadius(5)
            .textBold()
            .iconStart(R.drawable.ic_success)
            .length(3000)
            .textSize(14f)
            .gravity(Gravity.TOP)
            .font(R.font.poppins_medium)
            .show()
    }

    fun showErrorToast(context: Context, message: String) {
        StyleableToast.Builder(context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(ContextCompat.getColor(context, R.color.warningToast))
            .solidBackground()
            .cornerRadius(5)
            .textBold()
            .textSize(14f)
            .iconStart(R.drawable.ic_success)
            .length(3000)
            .gravity(Gravity.TOP)
            .font(R.font.poppins_medium)
            .show()
    }
}