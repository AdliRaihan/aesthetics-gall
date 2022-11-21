package com.tibz.lpsimulation.common.extension

import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator

fun View.animateAlphaTo(to: Float): ViewPropertyAnimator? {
    return this.animate()
        .alpha(to)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setDuration(250)
}