package com.tibz.lpsimulation.common.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import androidx.core.content.ContextCompat
import com.tibz.lpsimulation.R
import java.io.IOException
import java.text.DecimalFormat
import java.text.NumberFormat

class SwiftHelper {
    companion object {
        // All One lined function

        fun Context.getJsonFile(filePath: String, callback: ((Boolean, String) -> Unit)?) {
            try {
                callback?.invoke(true, this.assets.open(filePath).bufferedReader().use {
                    it.readText()
                })
            }
            catch(error: IOException) {
                callback?.invoke(false, error.message ?: "Error")
            }
        }

        fun  Context.lpColor(color: Int): Int {
            return ContextCompat.getColor(this, color)
        }

        fun Context.lpDrawable(type: Int): Drawable? {
            return ContextCompat.getDrawable(this, type)
        }
    }
}