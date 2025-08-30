package com.example.brainwest_android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.brainwest_android.R

class ShaderTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (width > 0) {
            val textShader = LinearGradient(
                0f, 0f, width.toFloat(), 0f,
                intArrayOf(
                    ContextCompat.getColor(context, R.color.primary),
                    ContextCompat.getColor(context, R.color.secondary),
                    ContextCompat.getColor(context, R.color.thirdtinary)
                ),
                null,
                Shader.TileMode.CLAMP
            )
            paint.shader = textShader
        }
    }
}