package com.tibz.lpsimulation.common.extension

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator

fun View.animateAlphaTo(to: Float): ViewPropertyAnimator? {
    return this.animate()
        .alpha(to)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setDuration(250)
}

fun View.visibilityAnimation(viewValue: Int, targetValue: Int) {
    this.visibility = View.VISIBLE
    viewValue.let {
        var leftHeight = targetValue
        var rightHeight = 0
        if (viewValue == View.VISIBLE) {
            leftHeight = 0
            rightHeight = targetValue
        }

        if (this.layoutParams.height == rightHeight) return
        val va = ValueAnimator.ofInt(leftHeight, rightHeight)
        va.addUpdateListener {
            val lp = this.layoutParams
            lp.height = it.animatedValue as Int
            this.layoutParams = lp
        }
        va.start()
    }
}

fun View.shake() {
    ObjectAnimator.ofFloat(
        this,
        "translationX",
        0f, 25f, -25f, 12f, -12f, 6f, -6f, 0f)
        .setDuration(1000)
        .start()
}

fun View.boardingAnimate() {
    ObjectAnimator.ofFloat(
        this,
        "translationX",
        75f, -75f, 75f)
        .let {
            it.repeatMode = ValueAnimator.RESTART
            it.repeatCount = ValueAnimator.INFINITE
            it.duration = 50000
            it.start()
        }
}

fun View.animateLoader(): ObjectAnimator {
    return ObjectAnimator.ofFloat(
        this,
        "translationX",
        0f, 150f, 0f, -150f, 0f, 150f, 0f)
}