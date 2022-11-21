package com.tibz.lpsimulation.common.helper

import android.text.InputFilter
import android.widget.EditText

class SwiftEditTextHelper {
    companion object {
        fun EditText.setMaxLength(amount: Int) { this.filters = arrayOf(InputFilter.LengthFilter(amount)) }
    }
}