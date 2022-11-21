package com.tibz.lpsimulation.common.dataModel

data class DMPlayerJobInformation(
    var companyName: String? = null,
    var position: String? = null,
    var isActive: Boolean = false,
    var money: Long = 0,
    var experience: Long = 0
)