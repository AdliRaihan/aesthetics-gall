package com.tibz.lpsimulation.activities.controller.Home

import androidx.appcompat.app.AppCompatActivity

interface HomeVCDelegate {
    fun showLoader()
    fun hideLoader()
    fun routeTo(navigation: AppCompatActivity)
}