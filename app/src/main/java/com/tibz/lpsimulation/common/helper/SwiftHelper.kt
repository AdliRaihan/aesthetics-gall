package com.tibz.lpsimulation.common.helper

import android.content.Context
import android.text.Editable
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

    }
}