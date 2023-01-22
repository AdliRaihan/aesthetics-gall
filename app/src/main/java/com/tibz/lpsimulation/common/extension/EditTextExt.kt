package com.tibz.lpsimulation.common.extension

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.setViewTreeOnBackPressedDispatcherOwner
import java.lang.ref.WeakReference

class EditTextExt {
    companion object {
        fun EditText.interceptBackButton(
            rootView: View? = null,
            actionWhenDone: (() -> Unit)?,
            actionBack: ((Int) -> Unit)?) {
            setOnKeyListener { _, _, event ->
                if (event.keyCode == KeyEvent.KEYCODE_BACK)
                    actionBack?.invoke(-999)
                false
            }
            this.setOnEditorActionListener { _, code, event ->
                if (code == EditorInfo.IME_ACTION_GO || code == EditorInfo.IME_ACTION_NEXT)
                    actionWhenDone?.invoke()
                true
            }
            rootView?.addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
                if (this.isFocused)
                    actionBack?.invoke(view.height)
            }
        }
    }
}