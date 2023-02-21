package com.tibz.lpsimulation.common.extension

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.tibz.lpsimulation.R
import com.tibz.lpsimulation.databinding.BottomSheetUnsplashDownloadBinding
import com.tibz.lpsimulation.databinding.CompCheckboxBinding

class BindingExt {
    companion object {
        fun BottomSheetUnsplashDownloadBinding.setCheckBoxAt(context: Context, index: Int) {
            // Resetting all checkboxes
            this.full.checkbox.typeface = ResourcesCompat.getFont(context, R.font.fasr)
            this.regular.checkbox.typeface = ResourcesCompat.getFont(context, R.font.fasr)
            this.low.checkbox.typeface = ResourcesCompat.getFont(context, R.font.fasr)
            when (index) {
                0 -> this.full.checkbox.typeface = ResourcesCompat.getFont(context, R.font.fasb)
                1 -> this.regular.checkbox.typeface = ResourcesCompat.getFont(context, R.font.fasb)
                2 -> this.low.checkbox.typeface = ResourcesCompat.getFont(context, R.font.fasb)
            }
        }
    }
}