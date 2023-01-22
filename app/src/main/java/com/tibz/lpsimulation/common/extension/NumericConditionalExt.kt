package com.tibz.lpsimulation.common.extension

fun Int.getIf(condition: Boolean, orElse: Int): Int {
    if (condition)
        return this
    return orElse
}
fun Int.orElse(): Int {
    return this
}
fun Float.getIf(condition: Boolean, orElse: Float): Float {
    if (condition)
        return this
    return orElse
}
fun Float.orElse(): Float {
    return this
}