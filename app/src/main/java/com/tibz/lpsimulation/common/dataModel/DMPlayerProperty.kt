package com.tibz.lpsimulation.common.dataModel

data class DMPlayerProperty(
    var name: String? = null,
    var valueBuy: Long = 0,
    var valueSell: Long = 0,
    var quality: Int = 0,
    var onMortgage: Boolean = false,
    var mortgageMonthly: Long = 0,
    var mortgageLeft: Int = 0
)