package com.tibz.lpsimulation.common.extension

import kotlin.random.Random
import kotlin.random.nextLong

fun randomizerByRange(from: Long, to: Long): Long = Random.nextLong(from..to)

fun Int?.optionalTo(defaultValue: Int = 0): Int {
    if (this == null) return defaultValue
    return this
}

fun Int.divideEqual(): Int {
    val moddedValue = this%3
    if (moddedValue > 0)
        return (this + (3 - moddedValue)) / 3
    return this/3
}
