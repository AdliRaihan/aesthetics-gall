package com.tibz.lpsimulation.activities.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.R
import com.tibz.lpsimulation.databinding.BaseControllerViewBinding
import java.lang.reflect.Array.set

open class BaseViewController: AppCompatActivity() {

    companion object {
        var stringTitle: String = ""
        var stringDescription: String = ""
    }

    // MARK: All Variables goes here
    lateinit var parentViewBinding: BaseControllerViewBinding
    var getTitle: String = stringTitle
    var getString: String = stringDescription

    // MARK: Computed Variables
    var setTitle: String?
        get() = parentViewBinding.navigationTitle.text.toString()
        set(value) {
            parentViewBinding.navigationTitle.text = value
            if (value == null) parentViewBinding.baseNavigationView.visibility = View.GONE
            else parentViewBinding.baseNavigationView.visibility = View.VISIBLE
        }

    val parentView: View
        get() = parentViewBinding.root

    // MARK: Lifecycles
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewBinding = BaseControllerViewBinding.inflate(layoutInflater)
        setContentView(parentViewBinding.root)
    }

    // MARK: Similar to add to superview
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        parentFinished()
    }

    fun setupUI() {
        // later
    }

    fun setParentContentView(viewBinding: ViewBinding) {
        parentViewBinding
            .baseControllerLinearLayout
            .addView(viewBinding.root)
    }

    // MARK: Caller for child view
    open fun parentFinished() { }

}