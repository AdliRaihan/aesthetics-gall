package com.tibz.lpsimulation.activities.controller.Setup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.Loader
import com.squareup.picasso.Picasso
import com.tibz.lpsimulation.activities.controller.Home.HomeVC
import com.tibz.lpsimulation.activities.view.LoaderView
import com.tibz.lpsimulation.common.dataModel.DMGame
import com.tibz.lpsimulation.common.dataModel.DMGame.Companion.loadSave
import com.tibz.lpsimulation.common.dataModel.DMPlayerInformation
import com.tibz.lpsimulation.common.extension.navigation
import com.tibz.lpsimulation.common.extension.shake
import com.tibz.lpsimulation.common.extension.visibilityAnimation
import com.tibz.lpsimulation.databinding.VcSetupBinding
import java.lang.ref.WeakReference

class SetupVC: AppCompatActivity(), SetupVM.ISetup {
    private lateinit var privateBinding: VcSetupBinding
    private var vm: SetupVM = SetupVM()
    private lateinit var loaderView: LoaderView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privateBinding = VcSetupBinding.inflate(this.layoutInflater)
        vm.delegate = WeakReference(this)
        setContentView(privateBinding.root)
        setupUI()
    }
    override fun onResume() {
        super.onResume()
    }
    private fun setupUI() {
        loaderView = LoaderView.createUI(this)
        privateBinding.backButton.setOnClickListener {
            this.finish()
        }
        privateBinding.btnNext.setOnClickListener {
            vm.source.username = privateBinding.usernameField.text.toString()
            vm.validate()
        }
    }
    override fun dataDidFailed(message: String?) {
        privateBinding.textError.visibilityAnimation(View.VISIBLE, 44)
        privateBinding.textError.text = message
        privateBinding.tempCardBox.shake()
    }
    override fun dataDidSuccess() {
        privateBinding.textError.visibilityAnimation(View.GONE, 44)
        routeToDashboard()
        //loaderDebug()
    }
    private fun routeToDashboard() {
        val newInstance = DMGame()
        newInstance.information = DMPlayerInformation()
        newInstance.information?.apply {
            this.name = vm.source.username
            this.age = 0
        }
        DMGame.resetSave(this, newInstance)
        this.navigation(HomeVC())
    }
    private fun loaderDebug() {
        loaderView.display(this, privateBinding.root)
    }
}