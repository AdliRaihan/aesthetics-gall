package com.tibz.lpsimulation.activities.reusable.bottomsheets

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tibz.lpsimulation.databinding.BottomSheetConfirmationBinding

class BottomSheetView {
    var delegate: BottomSheetProtocol? = null
    var dialog: BottomSheetDialog? = null
    var onDismissAction: (() -> Unit)? = null
    companion object {
        fun create(
            context: Context,
            vb: ViewBinding,
            delegate: BottomSheetProtocol,
            onDismissAction: (() -> Unit)? = null
        ): BottomSheetView {
            val tBS = BottomSheetView()
            tBS.dialog = BottomSheetDialog(context)
            tBS.delegate = delegate
            tBS.dialog?.setContentView(vb.root)
            tBS.dialog?.setOnDismissListener {
                // do delegate
                onDismissAction?.invoke()
            }
            return tBS
        }
    }

}