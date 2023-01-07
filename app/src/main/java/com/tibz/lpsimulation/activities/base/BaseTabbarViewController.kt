package com.tibz.lpsimulation.activities.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.activities.tabbar.HomeGameView
import com.tibz.lpsimulation.activities.tabbar.HomeTabbarView
import com.tibz.lpsimulation.common.extension.animateAlphaTo
import com.tibz.lpsimulation.databinding.BaseControllerTabbarViewBinding

// This is standalone so ....
class BaseTabbarViewController: AppCompatActivity() {

    lateinit var parentViewBinding: BaseControllerTabbarViewBinding

    private lateinit var controllers: Array<Fragment>

    private var currentIndicator: View? = null
    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewBinding = BaseControllerTabbarViewBinding.inflate(layoutInflater)
        setContentView(parentViewBinding.root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupUI()
    }

    private fun setupUI() {
        controllers = arrayOf(
            HomeGameView(this)
            // HomeTabbarView(this)
        )
        setupBind()
    }

    private fun setupBind() {
        var willSelectedIndex = 0

        parentViewBinding.firstMenu.setOnClickListener {
            willSelectedIndex = 0
            setViewIndicator(parentViewBinding.firstMenuIndicator)
        }

        parentViewBinding.secondMenu.setOnClickListener {

            setViewIndicator(parentViewBinding.secondMenuIndicator)
        }

        parentViewBinding.thirdMenu.setOnClickListener {

            setViewIndicator(parentViewBinding.thirdMenuIndicator)
        }

        if (willSelectedIndex == selectedIndex) return

        setFragment(controllers[willSelectedIndex])

        selectedIndex = willSelectedIndex
    }

    private fun setFragment(fragment: Fragment) {
        parentViewBinding
            .baseFragmentControl
            .animateAlphaTo(0f)?.withEndAction {

                supportFragmentManager
                    .beginTransaction()
                    .replace(parentViewBinding.baseFragmentControl.id, fragment)
                    .commit()

                parentViewBinding
                    .baseFragmentControl
                    .animateAlphaTo(1f)?.start()
            }
    }

    private fun setViewIndicator(newIndicator: View) {
        if (newIndicator.id == currentIndicator?.id) return

        newIndicator.animate()
            .scaleX(1.0f)
            .setDuration(250)
            .start()

        currentIndicator?.animate()
            ?.scaleX(0.0f)
            ?.setDuration(250)
            ?.start()

        currentIndicator = newIndicator
    }
}