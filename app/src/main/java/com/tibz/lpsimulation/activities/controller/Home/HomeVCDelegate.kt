package com.tibz.lpsimulation.activities.controller.Home

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

interface HomeVCDelegate {
    fun showLoader()
    fun hideLoader()
    fun routeTo(navigation: AppCompatActivity)
    fun getPermission(): ActivityResultLauncher<Array<String>>
    fun setPermission(action: (() -> Unit)?)
}