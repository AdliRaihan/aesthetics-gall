package com.tibz.lpsimulation.activities.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.text.Layout.Alignment
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.loader.content.Loader
import com.tibz.lpsimulation.R
import com.tibz.lpsimulation.common.extension.animateAlphaTo
import com.tibz.lpsimulation.common.extension.animateLoader
import java.awt.font.TextAttribute

class LoaderView {
    lateinit var indicatorAnimation: ObjectAnimator
    lateinit var indicator: View
    lateinit var lm: LinearLayout
    lateinit var text: TextView
    var isShowing = false
    companion object {
        fun createUI(context: Context): LoaderView {
            var loaderView = LoaderView()
            val indicator = LinearLayout(context)
            indicator.background = ContextCompat.getDrawable(context, R.drawable.background_gradient_testing)

            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.gravity = Gravity.CENTER
            linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.blackHalf))
            linearLayout.alpha = 0f

            val textLoading = TextView(context)
            textLoading.alpha = 0f
            textLoading.translationY = 350f
            textLoading.typeface = ResourcesCompat.getFont(context, R.font.open_sans_semibold)
            textLoading.text = "Did you know The word “Photography” comes from the Greek, meaning to draw with light. The earliest known use of the word photograph as we know it was in 1839 by the astronomer Sir John Herschel."
            textLoading.textSize = 14f
            textLoading.textAlignment = View.TEXT_ALIGNMENT_CENTER
            linearLayout.addView(indicator)
            linearLayout.addView(textLoading)

            indicator.setPadding(0,0,0, 24)
            loaderView.lm = linearLayout
            loaderView.text = textLoading
            loaderView.indicator = indicator
            loaderView.indicatorAnimation = indicator.animateLoader()
            return loaderView
        }
    }
    fun display(context: Context, toView: ConstraintLayout) {
        isShowing = true
        toView.addView(lm)
        if (lm.layoutParams == null) return

        text.setPadding(32, 24, 32, 0)
        lm.bottom = toView.bottom
        lm.top = toView.top
        lm.left = toView.left
        lm.right = toView.right
        lm.layoutParams.height = toView.layoutParams.height
        lm.layoutParams.width = toView.layoutParams.width
        indicator.layoutParams.height = 15
        indicator.layoutParams.width = 75
        lm.setPadding(24, 24, 24, 24)
        val systemService = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        systemService.hideSoftInputFromWindow(toView.windowToken, 0)

        text.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(500)
            .start()

        lm.animateAlphaTo(1f)


        indicatorAnimation.also {
            it.repeatMode = ValueAnimator.RESTART
            it.repeatCount = ValueAnimator.INFINITE
            it.duration = 2500
            it.start()
        }
    }
    fun hide(from: ConstraintLayout) {
        isShowing = false
        indicatorAnimation.cancel()
        lm.animateAlphaTo(0f)
        text.animate()
            .translationY(350f)
            .alpha(0f)
            .setDuration(500)
            .withEndAction {
                from.removeView(lm)
            }
            .start()
    }
}