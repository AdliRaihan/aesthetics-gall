package com.tibz.lpsimulation.activities.controller.Home

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.tibz.lpsimulation.activities.controller.Home.VP.ViewPagerPhotosView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftPager
import com.tibz.lpsimulation.activities.view.LoaderView
import com.tibz.lpsimulation.common.extension.navigation
import com.tibz.lpsimulation.databinding.HomeFragmentsWalletUserBinding
import com.tibz.lpsimulation.databinding.VcHomeBinding
import java.lang.ref.WeakReference

class HomeVC: AppCompatActivity(), HomeVCDelegate {
    private lateinit var privateBinding: VcHomeBinding
    private lateinit var homeVCPager: SwiftPager
    private lateinit var bindingone: HomeFragmentsWalletUserBinding
    // pages
    private lateinit var viewPagerPhotosView: ViewPagerPhotosView
    // Loader view
    private lateinit var loaderView: LoaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privateBinding = VcHomeBinding.inflate(this.layoutInflater)
        setContentView(privateBinding.root)
        setupUI()
        onBackPressedDispatcher.addCallback {
            privateBinding.root.requestFocus()
        }
    }

    private fun setupUI() {
        loaderView = LoaderView.createUI(this)
        viewPagerPhotosView = ViewPagerPhotosView()
        viewPagerPhotosView.homeDelegate = WeakReference(this)
        bindingone = HomeFragmentsWalletUserBinding.inflate(this.layoutInflater)
        homeVCPager = SwiftPager(this, arrayOf(
            viewPagerPhotosView
        ))
        privateBinding.viewPageHome.adapter = homeVCPager
        privateBinding.viewPageHome.isUserInputEnabled = false
    }
    override fun showLoader() {
        loaderView.display(this, privateBinding.root)
    }
    override fun hideLoader() {
        if (loaderView.isShowing)
            loaderView.hide(privateBinding.root)
    }

    override fun routeTo(navigation: AppCompatActivity) {
        this.navigation(navigation)
    }
}