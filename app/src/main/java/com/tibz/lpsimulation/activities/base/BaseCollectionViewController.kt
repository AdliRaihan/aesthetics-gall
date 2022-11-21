package com.tibz.lpsimulation.activities.base

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.tibz.lpsimulation.common.extension.popController
import com.tibz.lpsimulation.databinding.BaseControllerCollectionViewBinding

open class BaseCollectionViewController: AppCompatActivity() {

    companion object {
        var stringTitle: String = ""
        var stringDescription: String = ""
    }

    var titleNavigation: String? = null
    lateinit var parentViewBinding: BaseControllerCollectionViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewBinding = BaseControllerCollectionViewBinding.inflate(layoutInflater)
        setContentView(parentViewBinding.root)
        setupUI()
    }

    override fun onResume() {
        super.onResume()
        parentViewBinding.navigationTitle.text = this.titleNavigation
    }

    // MARK: Similar to add to superview
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        parentViewBinding.navigationTitle.text = this.titleNavigation

        if (stringTitle != "") {
            parentViewBinding.backButton.setOnClickListener {
                this.popController()
            }

            onBackPressedDispatcher.addCallback {
                this@BaseCollectionViewController.popController()
            }
        }

        parentFinished()
    }

    private fun setupUI() {
        this.titleNavigation = stringTitle

    }

    // For Child
    open fun parentFinished() { }
}