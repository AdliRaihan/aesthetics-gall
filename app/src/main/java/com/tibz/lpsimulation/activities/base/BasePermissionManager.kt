package com.tibz.lpsimulation.activities.base

import android.Manifest
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.common.helper.LPPermissionManager
import com.tibz.lpsimulation.common.helper.LPPhotoManager

class BasePermissionManager(
    private var controllable: AppCompatActivity? = null,
    private var controllableFragment: Fragment? = null
) {
    // Camera permission
    private var cameraPermission: ActivityResultLauncher<String>? = null
    private var takePhotoPermission: ActivityResultLauncher<String>? = null

    // Gallery Permission
    private var galleryPermission: ActivityResultLauncher<String>? = null
    // Gallery Permission - Get Gallery
    private var galleryPicker: ActivityResultLauncher<Intent>? = null

    fun registerResultGallery(callback: ((ActivityResult) -> Unit)?) {
        galleryPicker = controllableFragment!!.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { callback?.invoke(it) }
        registerPermissionGallery()
    }

    fun launchGallery() {
        galleryPermission?.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun registerPermissionGallery() {
        galleryPermission = controllableFragment!!.let { fragment ->
            LPPermissionManager
                .registerForPermissionCamera(fragment) { startPickGallery() }
        }
    }

    private fun startPickGallery() {
        val gallery = Intent(Intent.ACTION_PICK)
        gallery.type = "image/*"
        galleryPicker!!.launch(gallery)
    }

    private fun registerForActivity() {

    }


}