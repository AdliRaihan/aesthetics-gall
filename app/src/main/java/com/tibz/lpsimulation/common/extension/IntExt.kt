package com.tibz.lpsimulation.common.extension

import kotlin.random.Random
import kotlin.random.nextLong

fun randomizerByRange(from: Long, to: Long): Long = Random.nextLong(from..to)
