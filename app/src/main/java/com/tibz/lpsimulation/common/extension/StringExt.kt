package com.tibz.lpsimulation.common.extension

import android.text.Editable
import java.text.DecimalFormat
import java.text.NumberFormat

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun String.removeCurrency(): String = this.replace(",", "")

fun String.currencyFormat(withSymbol: String? = ""): String {
    val formatter: NumberFormat = DecimalFormat("#,###")

    val doubleVal = this.toDoubleOrNull()

    return if (doubleVal != null)
        withSymbol + formatter.format(doubleVal)
    else
        ""
}