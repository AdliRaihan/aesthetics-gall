package com.tibz.lpsimulation.activities.controller.Home

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var permissionWrite: ActivityResultLauncher<Array<String>>
    private var permissionSucceedAction: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        registerPermission()
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
        viewPagerPhotosView = ViewPagerPhotosView(this)
        viewPagerPhotosView.homeDelegate = WeakReference(this)
        bindingone = HomeFragmentsWalletUserBinding.inflate(this.layoutInflater)
        homeVCPager = SwiftPager(this, arrayOf(
            viewPagerPhotosView
        ))
        privateBinding.viewPageHome.adapter = homeVCPager
        privateBinding.viewPageHome.isUserInputEnabled = false
    }
    private fun registerPermission() {
        permissionWrite = this
            .registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) {
                if (it[Manifest.permission.READ_EXTERNAL_STORAGE] == true)
                    permissionSucceedAction?.invoke()
        }
    }
    override fun getPermission(): ActivityResultLauncher<Array<String>> = permissionWrite
    override fun setPermission(action: (() -> Unit)?) {
        permissionSucceedAction = action
    }
    override fun showLoader() {
        loaderView.displayBottom(this, privateBinding.root)
        //loaderView.display(this, privateBinding.root)
    }
    override fun hideLoader() {
        if (loaderView.isShowing)
            loaderView.hide(privateBinding.root)
    }
    override fun routeTo(navigation: AppCompatActivity) {
        this.navigation(navigation)
    }
}