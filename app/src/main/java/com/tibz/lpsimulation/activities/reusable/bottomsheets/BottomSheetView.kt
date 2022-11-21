package com.tibz.lpsimulation.activities.reusable.bottomsheets

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetView {
    var delegate: BottomSheetProtocol? = null
    var dialog: BottomSheetDialog? = null

    companion object {
        fun create(context: Context, vb: ViewBinding, delegate: BottomSheetProtocol): BottomSheetView {
            val tBS = BottomSheetView()
            tBS.dialog = BottomSheetDialog(context)
            tBS.delegate = delegate
            tBS.dialog?.setContentView(vb.root)
            tBS.dialog?.setOnDismissListener {
                // do delegate
            }
            return tBS
        }
    }

}